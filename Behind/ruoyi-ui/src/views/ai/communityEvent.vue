<template>
  <div class="app-container">
    <h1>AI 社区事件中心（主动感知）</h1>
    <el-table :data="eventList" border>
      <el-table-column label="事件标题" prop="eventTitle" />
      <el-table-column label="事件类型" prop="eventType" />
      <el-table-column label="AI建议" prop="aiSuggest" />
      <el-table-column label="状态" prop="status" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="primary" @click="publishNotice(scope.row)">一键公告</el-button>
          <el-button type="success" @click="createWorkOrder(scope.row)">一键派单</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { listAiCommunityEvent } from "@/api/ai/event";

export default {
  name: "AiCommunityEvent",
  data() {
    return {
      eventList: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    async getList() {
      const res = await listAiCommunityEvent({});
      this.eventList = (res && res.rows) ? res.rows : [];
    },
    publishNotice(row) {
      alert("AI自动生成社区公告：" + row.aiSuggest);
    },
    createWorkOrder(row) {
      alert("AI自动生成物业工单：" + row.aiSuggest);
    }
  }
};
</script>
