import config from './config'

export default {
  data() {
    return {
      activity: {},
      hasRegistered: false,
      showPointsToast: false, // 积分奖励弹窗显示状态
      showDeductToast: false, // 积分扣除弹窗显示状态
      pointsNum: 0, // 动画数字
      targetPoints: 50, // 目标积分
      isDeduct: false, // 是否为扣除积分
      showSuccess: false // 原有的成功提示
    };
  },
  onLoad(options) {
    if (options.activity) {
      // 🔥 修复1：用 $set 初始化响应式数据，触发视图刷新
      const activityData = JSON.parse(decodeURIComponent(options.activity));
      // imageUrl已由community.vue通过getImageById设置，直接使用；无则兜底
      if (!activityData.imageUrl) {
        const imageMap = {
          1: "/static/activity01.jpg",
          2: "/static/activity02.webp",
          3: "/static/activity03.jpg",
          4: "/static/activity04.jpg",
          5: "/static/activity05.webp",
          6: "/static/activity_show.jpg",
          7: "/static/activity_tree.jpg"
        };
        activityData.imageUrl = imageMap[activityData.id] || "/static/activity01.jpg";
      }
      // 关键：用$set初始化activity，保证响应式
      this.$set(this, 'activity', activityData);
      
      // 初始化报名状态，同样用$set
      const isRegistered = uni.getStorageSync("reg_" + activityData.id) || false;
      this.$set(this, 'hasRegistered', isRegistered);
    }
  },

  methods: {
    previewImage() {
      uni.previewImage({ urls: [this.activity.imageUrl] });
    },

    toggleRegister() {
      const isRegister = !this.hasRegistered;
      const url = isRegister
        ? config.resolveUrl('/community/activity/register')
        : config.resolveUrl('/community/activity/cancelRegister');

      // 获取token
      const token = uni.getStorageSync('community_token');
      if (!token) {
        uni.showToast({ title: '请先登录', icon: 'none' });
        return;
      }
      
      uni.request({
        url: url,
        method: "POST",
        data: { activityId: this.activity.id },
        header: { 
          "Content-Type": "application/json",
          ...(token && { "Authorization": "Bearer " + token })
        },
        success: (res) => {
          if (res.data.code === 200) {
            // 🔥 修复2：修改报名状态，用$set触发视图刷新
            const newRegisteredState = !this.hasRegistered;
            this.$set(this, 'hasRegistered', newRegisteredState);
            
            // 🔥 修复3：修改活动人数，深拷贝后更新，保证响应式
            const newActivity = { ...this.activity }; // 深拷贝避免直接修改原对象
            newActivity.count += newRegisteredState ? 1 : -1;
            this.$set(this, 'activity', newActivity);
            
            // 保存报名状态到缓存
            uni.setStorageSync("reg_" + this.activity.id, newRegisteredState);

            if (isRegister) {
              // 显示精美的积分奖励动画
              this.isDeduct = false;
              this.showPointsAnimation();
              
              // 完成活动报名任务
              uni.request({
                  url: config.resolveUrl('/community/task/complete/activity'),
                  method: 'POST',
                  header: {
                    'Authorization': 'Bearer ' + token
                  },
                  success: (taskRes) => {
                    console.log('完成活动报名任务:', taskRes.data);
                  },
                  fail: (err) => {
                    console.error('完成活动报名任务失败:', err);
                  }
                });
            } else {
              // 显示积分扣除动画
              this.isDeduct = true;
              this.showPointsAnimation();
            }

            // 通知上一页刷新数据
            const pages = getCurrentPages();
            const prev = pages[pages.length - 2];
            if (prev && prev.loadActivityFromBackend) {
              prev.loadActivityFromBackend();
            }
          } else {
            uni.showToast({
              title: res.data.msg || "操作失败",
              icon: "none"
            });
          }
        },
        // 🔥 新增：错误处理，避免接口失败无提示
        fail: (err) => {
          uni.showToast({
            title: "操作失败，请重试",
            icon: "none"
          });
          console.error("报名/取消报名接口失败：", err);
        }
      });
    },

    // 积分数字动画
    showPointsAnimation() {
      // 🔥 修复4：动画中修改pointsNum，用$set保证实时刷新
      if (this.isDeduct) {
        this.$set(this, 'showDeductToast', true);
        this.$set(this, 'pointsNum', this.targetPoints);
      } else {
        this.$set(this, 'showPointsToast', true);
        this.$set(this, 'pointsNum', 0);
      }
      
      // 数字跳动动画
      const timer = setInterval(() => {
        if (this.isDeduct) {
          // 扣除积分动画：从50减到0
          if (this.pointsNum > 0) {
            const newNum = this.pointsNum - 5;
            this.$set(this, 'pointsNum', newNum < 0 ? 0 : newNum);
          } else {
            clearInterval(timer);
            // 3秒后关闭弹窗
            setTimeout(() => {
              this.$set(this, 'showDeductToast', false);
            }, 3000);
          }
        } else {
          // 增加积分动画：从0加到50
          if (this.pointsNum < this.targetPoints) {
            const newNum = this.pointsNum + 5;
            this.$set(this, 'pointsNum', newNum > this.targetPoints ? this.targetPoints : newNum);
          } else {
            clearInterval(timer);
            // 3秒后关闭弹窗
            setTimeout(() => {
              this.$set(this, 'showPointsToast', false);
            }, 3000);
          }
        }
      }, 50);
    }
  }
};
