<template>
  <view class="survey-page">
    <!-- 头部 -->
    <view class="survey-header">
      <text class="survey-title">🎯 满意度调研</text>
      <text class="survey-subtitle">您的反馈对我们很重要</text>
    </view>

    <!-- 调研表单 -->
    <view class="survey-form">
      <!-- 整体满意度 -->
      <view class="form-section">
        <text class="section-title">1. 整体满意度</text>
        <text class="section-desc">请对我们的社区服务整体满意度进行评价</text>
        <view class="rating-container">
          <text 
            v-for="star in 5" 
            :key="star"
            class="star" 
            :class="{ active: rating >= star }"
            @click="setRating(star)"
          >⭐</text>
        </view>
        <text class="rating-text">{{ getRatingText(rating) }}</text>
      </view>

      <!-- 具体服务评价 -->
      <view class="form-section">
        <text class="section-title">2. 具体服务评价</text>
        <view class="service-item" v-for="(service, index) in services" :key="index">
          <text class="service-name">{{ service.name }}</text>
          <view class="service-rating">
            <text 
              v-for="star in 5" 
              :key="star"
              class="small-star" 
              :class="{ active: service.rating >= star }"
              @click="setServiceRating(index, star)"
            >⭐</text>
          </view>
        </view>
      </view>

      <!-- 建议和意见 -->
      <view class="form-section">
        <text class="section-title">3. 建议和意见</text>
        <textarea 
          class="suggestion-input" 
          v-model="suggestion" 
          placeholder="请输入您的建议和意见..."
          maxlength="500"
        ></textarea>
        <text class="char-count">{{ suggestion.length }}/500</text>
      </view>

      <!-- 提交按钮 -->
      <button class="submit-btn" @click="submitSurvey" :disabled="!canSubmit">
        {{ submitButtonText }}
      </button>
    </view>

    <!-- 提交成功弹窗 -->
    <view class="success-modal" v-if="showSuccessModal">
      <view class="success-mask" @click="showSuccessModal = false"></view>
      <view class="success-content">
        <view class="success-icon">🎉</view>
        <view class="success-title">提交成功！</view>
        <view class="success-desc">感谢您的反馈，积分已发放</view>
        <view class="success-points">+{{ points }} 积分</view>
        <button class="success-btn" @click="showSuccessModal = false; backToTaskCenter()">
          知道啦
        </button>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      rating: 0,
      services: [
        { name: '社区环境', rating: 0 },
        { name: '物业服务', rating: 0 },
        { name: '活动组织', rating: 0 },
        { name: '安全管理', rating: 0 },
        { name: '便民服务', rating: 0 }
      ],
      suggestion: '',
      showSuccessModal: false,
      points: 30
    }
  },
  computed: {
    canSubmit() {
      const ratingValid = this.rating > 0
      const servicesValid = this.services.every(service => service.rating > 0)
      const suggestionValid = this.suggestion.trim().length > 0
      console.log('canSubmit check:', { ratingValid, servicesValid, suggestionValid })
      return ratingValid && servicesValid && suggestionValid
    },
    submitButtonText() {
      if (!this.rating) {
        return '请选择整体满意度'
      } else if (!this.services.every(service => service.rating > 0)) {
        return '请完成所有服务评价'
      } else if (!this.suggestion.trim()) {
        return '请输入建议和意见'
      } else {
        return '提交调研'
      }
    }
  },
  methods: {
    getRatingText(rating) {
      const texts = ['请选择', '非常不满意', '不满意', '一般', '满意', '非常满意']
      return texts[rating]
    },
    async submitSurvey() {
      if (!this.canSubmit) {
        uni.showToast({
          title: '请完成所有问题',
          icon: 'none'
        })
        return
      }

      const token = uni.getStorageSync('community_token')
      if (!token) {
        uni.showToast({
          title: '请先登录',
          icon: 'none'
        })
        return
      }

      uni.showLoading({ title: '提交中...' })

      try {
        // 提交调研数据
        const surveyData = {
          rating: this.rating,
          services: JSON.stringify(this.services),
          suggestion: this.suggestion
        }

        // 调用后端API提交调研数据
        const submitRes = await this.request({
          url: 'http://127.0.0.1:8080/community/survey/submit',
          method: 'POST',
          header: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
          },
          data: surveyData
        })

        if (submitRes.data.code !== 200) {
          uni.showToast({
            title: '提交失败，请重试',
            icon: 'none'
          })
          return
        }

        console.log('提交调研数据成功:', surveyData)

        // 完成调研任务
        const taskRes = await this.request({
          url: 'http://127.0.0.1:8080/community/task/complete/survey',
          method: 'POST',
          header: {
            'Authorization': 'Bearer ' + token
          }
        })

        if (taskRes.data.code === 200) {
          this.showSuccessModal = true
        } else {
          uni.showToast({
            title: '提交失败，请重试',
            icon: 'none'
          })
        }
      } catch (e) {
        console.error('提交调研失败:', e)
        uni.showToast({
          title: '提交失败，请重试',
          icon: 'none'
        })
      } finally {
        uni.hideLoading()
      }
    },
    backToTaskCenter() {
      uni.navigateBack()
    },
    setRating(star) {
      this.rating = star
    },
    setServiceRating(index, star) {
      // 创建一个新的数组来触发响应式更新
      this.services = this.services.map((service, i) => {
        if (i === index) {
          return { ...service, rating: star }
        }
        return service
      })
    },
    request(options) {
      return new Promise((resolve, reject) => {
        uni.request({
          ...options,
          success: (res) => {
            resolve(res)
          },
          fail: (err) => {
            reject(err)
          }
        })
      })
    }
  }
}
</script>

<style scoped>
  .survey-page {
    min-height: 100vh;
    background-color: #f5f7fa;
    padding: 40rpx 30rpx;
  }

  .survey-header {
    text-align: center;
    margin-bottom: 60rpx;
  }

  .survey-title {
    font-size: 40rpx;
    font-weight: bold;
    color: #333;
    display: block;
    margin-bottom: 10rpx;
  }

  .survey-subtitle {
    font-size: 26rpx;
    color: #666;
  }

  .survey-form {
    background: #fff;
    border-radius: 24rpx;
    padding: 40rpx 30rpx;
    box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.05);
  }

  .form-section {
    margin-bottom: 40rpx;
  }

  .section-title {
    font-size: 32rpx;
    font-weight: bold;
    color: #333;
    display: block;
    margin-bottom: 12rpx;
  }

  .section-desc {
    font-size: 24rpx;
    color: #666;
    margin-bottom: 24rpx;
  }

  .rating-container {
    display: flex;
    gap: 20rpx;
    margin-bottom: 16rpx;
  }

  .star {
    font-size: 56rpx;
    cursor: pointer;
    transition: all 0.3s;
  }

  .star:not(.active) {
    color: #ddd;
  }

  .star.active {
    transform: scale(1.2);
    color: #ffd700;
    text-shadow: 0 0 10rpx rgba(255, 215, 0, 0.8);
  }

  .rating-text {
    font-size: 28rpx;
    color: #667eea;
    font-weight: bold;
  }

  .service-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 24rpx;
  }

  .service-name {
    font-size: 26rpx;
    color: #333;
    flex: 1;
  }

  .service-rating {
    display: flex;
    gap: 8rpx;
  }

  .small-star {
    font-size: 36rpx;
    cursor: pointer;
    transition: all 0.3s;
  }

  .small-star:not(.active) {
    color: #ddd;
  }

  .small-star.active {
    transform: scale(1.1);
    color: #ffd700;
    text-shadow: 0 0 8rpx rgba(255, 215, 0, 0.6);
  }

  .suggestion-input {
    width: 100%;
    min-height: 200rpx;
    padding: 20rpx;
    border: 2rpx solid #e0e0e0;
    border-radius: 16rpx;
    font-size: 26rpx;
    color: #333;
    resize: none;
    margin-bottom: 12rpx;
  }

  .char-count {
    font-size: 22rpx;
    color: #999;
    text-align: right;
  }

  .submit-btn {
    width: 100%;
    height: 96rpx;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: #fff;
    border: none;
    border-radius: 50rpx;
    font-size: 32rpx;
    font-weight: bold;
    margin-top: 40rpx;
  }

  .submit-btn:disabled {
    background: #ccc;
  }

  .success-modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 9999;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .success-mask {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.6);
  }

  .success-content {
    position: relative;
    background: #fff;
    border-radius: 30rpx;
    padding: 60rpx;
    text-align: center;
    z-index: 1;
    animation: scaleIn 0.3s ease-out;
  }

  @keyframes scaleIn {
    0% {
      transform: scale(0.8);
      opacity: 0;
    }
    100% {
      transform: scale(1);
      opacity: 1;
    }
  }

  .success-icon {
    font-size: 100rpx;
    margin-bottom: 20rpx;
  }

  .success-title {
    font-size: 36rpx;
    font-weight: bold;
    color: #333;
    margin-bottom: 16rpx;
  }

  .success-desc {
    font-size: 26rpx;
    color: #666;
    margin-bottom: 30rpx;
  }

  .success-points {
    font-size: 48rpx;
    font-weight: bold;
    color: #ff6b6b;
    margin-bottom: 40rpx;
  }

  .success-btn {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: #fff;
    border: none;
    border-radius: 40rpx;
    padding: 24rpx 80rpx;
    font-size: 32rpx;
    font-weight: bold;
  }
</style>
