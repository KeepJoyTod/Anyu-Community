<template>
  <div class="dashboard-container">
    <!-- 顶部标题 -->
    <div class="dashboard-header">
      <h2 class="title">安隅社区管理系统</h2>
      <p class="subtitle">打造智慧社区 · 提升服务品质</p>
    </div>

    <!-- 轮播图 -->
    <div class="carousel-box">
      <el-carousel
          height="380px"
          indicator-position="bottom"
          autoplay
          interval="5000"
          type="card"
      >
        <el-carousel-item v-for="(item, index) in carouselList" :key="index">
          <div class="carousel-item-wrap">
            <img :src="item.imgUrl" alt="轮播图片" class="carousel-img">
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>

    <!-- 数据面板 -->
    <div class="panel-box">
      <panel-group
          @handleSetLineChartData="handleSetLineChartData"
          :userCount="panelData.userCount"
          :messageCount="panelData.messageCount"
          :purchaseCount="panelData.purchaseCount"
          :shoppingCount="panelData.shoppingCount"
      />
    </div>

    <!-- 图表 -->
    <div class="chart-section">
      <div class="section-header">
        <h3>社区数据趋势分析</h3>
        <p>Data Trend Analysis</p>
      </div>
      <el-row class="chart-card">
        <line-chart :chart-data="lineChartData" />
      </el-row>
    </div>

    <!-- 底部版权 -->
    <div class="dashboard-footer">
      <p>© 2026 安隅社区管理系统 · 版权所有</p>
    </div>

    <!-- AI 悬浮按钮 -->
    <div class="ai-float-btn" @click="openAiDialog">
      <i class="el-icon-ai"></i>
    </div>

    <!-- AI 对话弹窗 -->
    <el-dialog
        :visible.sync="aiDialogVisible"
        title="安隅社区AI助手"
        width="600px"
        append-to-body
        class="ai-chat-dialog"
    >
      <div class="chat-container">
        <div class="chat-content" ref="chatContent">
          <!-- 消息列表 -->
          <div
              v-for="(item, index) in messageList"
              :key="index"
              :class="['chat-item', item.role === 'user' ? 'user-item' : 'ai-item']"
          >
            <div class="avatar">
              <i v-if="item.role === 'user'" class="el-icon-user"></i>
              <i v-else class="el-icon-ai"></i>
            </div>
            <div class="message-box">
              {{ item.content }}
            </div>
          </div>
          <!-- 加载中 -->
          <div v-if="loading" class="loading-tip">
            AI正在思考中...
          </div>
        </div>

        <div class="chat-input-box">
          <el-input
              v-model="inputMessage"
              type="textarea"
              :rows="3"
              placeholder="请输入您的问题..."
              @keyup.enter.native="sendMessage"
          />
          <el-button
              type="primary"
              icon="el-icon-send"
              @click="sendMessage"
              :loading="loading"
              style="margin-top: 10px"
          >
            发送
          </el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'
import { sendChatMessage } from '@/api/ai'
import { Carousel, CarouselItem } from 'element-ui'
import { getPanelData, getLinelData } from '@/api/home'

const lineChartData = {
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
}

export default {
  name: 'Index',
  components: {
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart,
    ElCarousel: Carousel,
    ElCarouselItem: CarouselItem
  },
  data() {
    return {
      lineChartData: {},
      carouselList: [
        { imgUrl: require('@/assets/home/img1.jpg'), title: '轮播图1' },
        { imgUrl: require('@/assets/home/img2.jpg'), title: '轮播图2' },
        { imgUrl: require('@/assets/home/img3.jpg'), title: '轮播图3' }
      ],
      panelData: {
        userCount: '',
        messageCount: '',
        purchaseCount: '',
        shoppingCount: ''
      },
      // AI相关
      aiDialogVisible: false,
      messageList: [],
      inputMessage: '',
      loading: false
    }
  },
  created() {
    this.getPanelData()
    this.getLinelData()
  },
  methods: {
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    },
    getPanelData() {
      getPanelData().then(res => {
        if (res.code === 200) {
          this.panelData = {
            userCount: res.userCount,
            messageCount: res.messageCount,
            purchaseCount: res.purchaseCount,
            shoppingCount: res.shoppingCount
          }
        }
      }).catch(err => {
        console.error('获取面板数据失败：', err)
      })
    },
    getLinelData() {
      getLinelData().then(res => {
        if (res.code == 200) {
          this.lineChartData = {
            countData: res.data.countData
          }
        }
      }).catch(err => {
        this.lineChartData = { countData: [0, 0, 0, 0, 0] }
      })
    },

    // 打开AI弹窗
    openAiDialog() {
      this.aiDialogVisible = true
      // 首次打开欢迎语
      if (this.messageList.length === 0) {
        this.messageList.push({
          role: 'ai',
          content: '你好，我是安隅社区AI助手，有什么可以帮您？'
        })
      }
    },

    // 发送消息给AI
    async sendMessage() {
      if (!this.inputMessage.trim()) return
      const userMsg = this.inputMessage
      // 加入用户消息
      this.messageList.push({
        role: 'user',
        content: userMsg
      })
      this.inputMessage = ''
      this.loading = true

      try {
        const res = await sendChatMessage({
          message: userMsg
        })
// 确认后端返回结构
        console.log("AI 响应：", res)
        this.messageList.push({
          role: 'ai',
          content: res.data || res // 兼容不同返回格式
        })
      } catch (err) {
        this.messageList.push({
          role: 'ai',
          content: 'AI服务异常，请稍后再试'
        })
      } finally {
        this.loading = false
        this.$nextTick(() => {
          this.$refs.chatContent.scrollTop = this.$refs.chatContent.scrollHeight
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
// 你原来的所有样式保留，我只追加AI样式
.dashboard-container {
  padding: 32px 40px;
  background: linear-gradient(135deg, #f5f7ff 0%, #eef2fc 35%, #e6edfb 70%, #dee7f9 100%);
  min-height: 100vh;
  box-sizing: border-box;
  position: relative;
}

.dashboard-header {
  background: linear-gradient(135deg, #165DFF 0%, #0FC6C2 55%, #36CFC9 100%);
  padding: 50px 36px;
  border-radius: 24px;
  text-align: center;
  margin-bottom: 32px;
  box-shadow: 0 15px 40px rgba(22, 93, 255, 0.28);
  color: #fff;
  position: relative;
  overflow: hidden;

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: radial-gradient(circle at 30% 80%, rgba(255,255,255,0.22) 0%, transparent 55%);
    pointer-events: none;
  }

  .title {
    font-size: 44px;
    font-weight: 800;
    margin: 0 0 14px 0;
    letter-spacing: 4px;
    text-shadow: 0 4px 10px rgba(0,0,0,0.18);
  }

  .subtitle {
    font-size: 20px;
    opacity: 0.98;
    margin: 0;
    font-weight: 400;
  }
}

.carousel-box {
  background: rgba(255, 255, 255, 0.97);
  backdrop-filter: blur(20px);
  padding: 32px;
  border-radius: 24px;
  margin-bottom: 32px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
  border: 1px solid rgba(255,255,255,0.7);
  transition: all 0.35s ease;

  &:hover {
    transform: translateY(-6px);
    box-shadow: 0 16px 40px rgba(0,0,0,0.12);
  }

  ::v-deep .el-carousel {
    border-radius: 20px;
    overflow: hidden;
    box-shadow: 0 8px 24px rgba(0,0,0,0.1);
  }
}

.carousel-item-wrap {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.carousel-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 20px;
}

.panel-box {
  margin-bottom: 32px;
  background: rgba(255, 255, 255, 0.97);
  backdrop-filter: blur(20px);
  padding: 28px;
  border-radius: 24px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
  border: 1px solid rgba(255,255,255,0.7);
  transition: all 0.35s ease;

  &:hover {
    transform: translateY(-6px);
    box-shadow: 0 16px 40px rgba(0,0,0,0.12);
  }
}

.chart-section {
  margin-bottom: 40px;
}

.section-header {
  display: flex;
  align-items: baseline;
  gap: 16px;
  margin-bottom: 24px;
  padding-left: 12px;

  h3 {
    font-size: 26px;
    font-weight: 700;
    color: #1d2129;
    margin: 0;
    position: relative;
    padding-left: 16px;

    &::before {
      content: '';
      position: absolute;
      left: 0;
      top: 50%;
      transform: translateY(-50%);
      width: 6px;
      height: 26px;
      background: linear-gradient(180deg, #165DFF, #0FC6C2);
      border-radius: 4px;
    }
  }

  p {
    font-size: 15px;
    color: #86909c;
    margin: 0;
    font-style: italic;
  }
}

.chart-card {
  background: rgba(255, 255, 255, 0.97);
  backdrop-filter: blur(20px);
  padding: 32px;
  border-radius: 24px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
  border: 1px solid rgba(255,255,255,0.7);
  transition: all 0.35s ease;

  &:hover {
    transform: translateY(-6px);
    box-shadow: 0 16px 40px rgba(0,0,0,0.12);
  }
}

.dashboard-footer {
  text-align: center;
  padding: 28px;
  background: rgba(255,255,255,0.9);
  backdrop-filter: blur(20px);
  border-radius: 20px;
  margin-top: 52px;
  box-shadow: 0 8px 24px rgba(0,0,0,0.06);

  p {
    margin: 0;
    font-size: 15px;
    background: linear-gradient(135deg, #165DFF 0%, #0FC6C2 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    font-weight: 600;
    letter-spacing: 1px;
  }
}

/* ===================== AI 样式 ===================== */
.ai-float-btn {
  position: fixed;
  right: 30px;
  bottom: 30px;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background: linear-gradient(135deg, #165DFF, #0FC6C2);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 24px;
  box-shadow: 0 8px 20px rgba(22, 93, 255, 0.3);
  cursor: pointer;
  transition: all 0.3s;
  z-index: 999;

  &:hover {
    transform: scale(1.1);
  }
}

.chat-container {
  height: 500px;
  display: flex;
  flex-direction: column;
}

.chat-content {
  flex: 1;
  padding: 10px;
  overflow-y: auto;
  background: #f7f8fa;
  border-radius: 12px;
  margin-bottom: 10px;
}

.chat-item {
  display: flex;
  margin-bottom: 16px;
  align-items: flex-start;

  .avatar {
    width: 36px;
    height: 36px;
    border-radius: 50%;
    background: linear-gradient(135deg, #165DFF, #0FC6C2);
    color: #fff;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
    margin-right: 10px;
  }

  .message-box {
    max-width: 70%;
    padding: 10px 14px;
    border-radius: 12px;
    line-height: 1.5;
    word-break: break-all;
  }
}

.user-item {
  flex-direction: row-reverse;

  .avatar {
    margin-right: 0;
    margin-left: 10px;
    background: #909399;
  }

  .message-box {
    background: #165DFF;
    color: #fff;
  }
}

.ai-item {
  .message-box {
    background: #fff;
    border: 1px solid #e4e7ed;
  }
}

.loading-tip {
  text-align: center;
  color: #909399;
  padding: 10px;
}

.chat-input-box {
  margin-top: 10px;
}

@media (max-width: 768px) {
  .dashboard-container {
    padding: 24px;
  }
  .dashboard-header {
    padding: 40px 24px;
    .title {
      font-size: 34px;
    }
  }
}
</style>
