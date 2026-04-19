<template>
  <div class="ai-event-container">
    <div class="header-card">
      <h2>🧠 AI主动社区事件中心</h2>
      <p>AI自动发现天气、安全、政策、公告并生成处置建议</p>
    </div>

    <el-card v-for="item in list" :key="item.id" class="event-card">
      <div class="card-header">
        <span class="title">{{ item.eventTitle }}</span>
        <el-tag :type="getTagType(item.eventLevel)">{{ item.eventLevel }}</el-tag>
      </div>
      <div class="content">{{ item.eventContent }}</div>
      <div class="suggest">
        <label>AI建议：</label>{{ item.aiSuggest }}
      </div>
      <div class="operate">
        <el-button type="success" @click="set(item.id,'1')">一键公告</el-button>
        <el-button type="primary" @click="set(item.id,'2')">一键派单</el-button>
        <el-button type="warning" @click="set(item.id,'3')">已完结</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import { listAiEvent, updateAiEventStatus } from "@/api/system/aiEvent";
export default {
  data() {
    return { list: [] };
  },
  created() { this.getList(); },
  methods: {
    async getList() {
      const res = await listAiEvent();
      this.list = res.rows;
    },
    async set(id, status) {
      await updateAiEventStatus(id, status);
      this.$message.success("操作成功");
      this.getList();
    },
    getTagType(l) {
      if(l=="urgent")return "danger";
      if(l=="warning")return "warning";
      return "success";
    }
  }
};
</script>

<style scoped>
.ai-event-container { padding: 20px; }
.header-card { background: linear-gradient(90deg,#1677ff,#0FC6C2);
  color:white; padding:30px; border-radius:16px; margin-bottom:20px; }
.event-card { margin-bottom:16px; border-radius:12px; }
.card-header { display:flex; justify-content:space-between; font-weight:bold; }
.suggest { color:#666; margin:10px 0; }
</style>
