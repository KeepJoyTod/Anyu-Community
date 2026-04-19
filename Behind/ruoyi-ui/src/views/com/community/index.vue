<template>
  <div class="community-container">
    <!-- 页面头部 -->
    <div class="page-header">
      <div class="left">
        <span class="title">🏘️ 小区综合管理平台</span>
        <p class="desc">小区服务 | 物业管理 | 数据监控 | 便民通知</p>
      </div>
      <div class="right">
        <el-button type="primary">编辑小区信息</el-button>
      </div>
    </div>

    <!-- 统计卡片 -->
    <div class="stats-card-box">
      <div class="card-item" v-for="(item, index) in stats" :key="index">
        <div class="left" :style="{ background: item.color }"></div>
        <div class="right">
          <p class="label">{{ item.title }}</p>
          <p class="value">{{ item.value }}</p>
        </div>
      </div>
    </div>

    <!-- 小区介绍区域（单个小区） -->
    <div class="intro-box">
      <div class="intro-card">
        <h3>🏡 小区基本信息</h3>
        <div class="info-row">
          <label>小区名称：</label>
          <span>阳光花园小区</span>
        </div>
        <div class="info-row">
          <label>详细地址：</label>
          <span>北京市海淀区花园路1号</span>
        </div>
        <div class="info-row">
          <label>建成时间：</label>
          <span>2015年06月</span>
        </div>
        <div class="info-row">
          <label>小区户数：</label>
          <span>1856户</span>
        </div>
        <div class="info-row">
          <label>物业公司：</label>
          <span>阳光智慧物业服务有限公司</span>
        </div>
        <p class="info-desc">
          本小区环境优美、绿化覆盖率高，配备24小时安保、智能门禁、公共健身区、儿童游乐区、地下车库等完善配套设施，致力于为居民提供安全、舒适、便捷的高品质居住环境。
        </p>
        <div class="tags">
          <span>智能管理</span>
          <span>安全监控</span>
          <span>物业服务</span>
          <span>绿化优质</span>
          <span>配套完善</span>
        </div>
      </div>
    </div>

    <!-- 内容区域 -->
    <div class="content-wrapper">
      <!-- 左侧：住户/房产管理 -->
      <div class="left-content">
        <div class="panel">
          <div class="panel-header">
            <span>📋 住户信息管理</span>
            <el-button link type="primary">全部住户</el-button>
          </div>
          <div class="panel-body">
            <el-table :data="userList" border stripe>
              <el-table-column prop="id" label="序号" width="70" />
              <el-table-column prop="name" label="住户姓名" />
              <el-table-column prop="room" label="房号" />
              <el-table-column prop="phone" label="联系电话" />
              <el-table-column label="状态" width="100">
                <template slot-scope="scope">
                  <el-tag type="success">已入住</el-tag>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </div>

      <!-- 右侧：公告 + 统计 -->
      <div class="right-content">
        <div class="panel">
          <div class="panel-header">🔔 小区最新公告</div>
          <div class="panel-body">
            <div class="notice-item" v-for="item in noticeList" :key="item.id">
              <div class="dot"></div>
              <span class="title">{{ item.title }}</span>
              <span class="date">{{ item.date }}</span>
            </div>
          </div>
        </div>

        <div class="panel">
          <div class="panel-header">📊 小区入住统计</div>
          <div class="panel-body chart-box">
            <div>入住率统计图表区域</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Community",
  data() {
    return {
      // 小区数据统计
      stats: [
        { title: '总户数', value: 1856, color: '#409EFC' },
        { title: '已入住', value: 1620, color: '#36D399' },
        { title: '公告数', value: 28, color: '#FBBD23' },
        { title: '设备数', value: 135, color: '#F87272' }
      ],
      // 住户列表
      userList: [
        { id: 1, name: '张三', room: '1号楼 1单元 101', phone: '13800138000' },
        { id: 2, name: '李四', room: '1号楼 1单元 102', phone: '13800138001' },
        { id: 3, name: '王五', room: '2号楼 2单元 201', phone: '13800138002' },
        { id: 4, name: '赵六', room: '2号楼 2单元 202', phone: '13800138003' }
      ],
      // 公告
      noticeList: [
        { id: 1, title: '关于小区停水通知', date: '2025-03-20' },
        { id: 2, title: '消防安全检查通知', date: '2025-03-18' },
        { id: 3, title: '小区绿化升级公告', date: '2025-03-15' }
      ]
    };
  },
  mounted() {
    console.log("单个小区管理页面加载完成");
  },
  methods: {}
};
</script>

<style scoped lang="scss">
.community-container {
  padding: 20px;
  background: linear-gradient(135deg, #f6f9fc 0%, #eef5ff 100%);
  min-height: 100vh;
  font-family: "Microsoft YaHei", sans-serif;
}

/* 头部 */
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  background: #fff;
  padding: 18px 24px;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.04);

  .left {
    display: flex;
    flex-direction: column;
    gap: 6px;
  }

  .title {
    font-size: 24px;
    font-weight: bold;
    color: #2c3e50;
    margin: 0;
  }

  .desc {
    font-size: 14px;
    color: #7b8f9f;
    margin: 0;
  }
}

/* 统计卡片 */
.stats-card-box {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
  margin-bottom: 20px;

  .card-item {
    background: #fff;
    border-radius: 12px;
    padding: 20px;
    display: flex;
    align-items: center;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
    transition: all 0.3s ease;

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
    }

    .left {
      width: 60px;
      height: 60px;
      border-radius: 10px;
      margin-right: 16px;
    }

    .right {
      flex: 1;

      .label {
        font-size: 14px;
        color: #666;
        margin: 0;
      }

      .value {
        font-size: 26px;
        font-weight: bold;
        color: #2c3e50;
        margin: 4px 0 0;
      }
    }
  }
}

/* 小区介绍 */
.intro-box {
  margin-bottom: 22px;
}

.intro-card {
  background: #fff;
  border-radius: 12px;
  padding: 22px 26px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.04);

  h3 {
    font-size: 18px;
    color: #2f4050;
    margin: 0 0 16px 0;
  }

  .info-row {
    display: flex;
    margin-bottom: 10px;
    font-size: 14px;

    label {
      width: 100px;
      font-weight: 500;
      color: #333;
    }

    span {
      color: #666;
    }
  }

  .info-desc {
    margin-top: 12px;
    line-height: 1.6;
    color: #666;
    font-size: 14px;
  }

  .tags {
    display: flex;
    gap: 10px;
    margin-top: 14px;
    flex-wrap: wrap;

    span {
      background: #e8f3ff;
      color: #409eff;
      padding: 4px 12px;
      border-radius: 20px;
      font-size: 13px;
    }
  }
}

/* 内容布局 */
.content-wrapper {
  display: flex;
  gap: 16px;

  .left-content {
    flex: 2;
  }

  .right-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 16px;
  }
}

/* 面板 */
.panel {
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.04);

  .panel-header {
    padding: 15px 20px;
    background: #fafbfc;
    border-bottom: 1px solid #eee;
    font-weight: 500;
    font-size: 15px;
    color: #333;
    display: flex;
    justify-content: space-between;
  }

  .panel-body {
    padding: 16px 20px;
  }
}

/* 公告 */
.notice-item {
  display: flex;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px dashed #eee;

  &:last-child {
    border-bottom: none;
  }

  .dot {
    width: 6px;
    height: 6px;
    background: #409eff;
    border-radius: 50%;
    margin-right: 10px;
  }

  .title {
    flex: 1;
    font-size: 14px;
  }

  .date {
    font-size: 12px;
    color: #999;
  }
}

/* 图表 */
.chart-box {
  height: 180px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f9fafb;
  border-radius: 8px;
  color: #999;
}
</style>
