<template>
  <el-dialog
    :visible.sync="visible"
    append-to-body
    :show-close="false"
    close-on-click-modal="false"
    close-on-press-escape="false"
    width="720px"
    custom-class="yang-ad-dialog"
  >
    <div class="ad-container">
      <div class="ad-left">
        <img
          src="@/assets/images/img.png"
          alt="山东高合杨师强"
          class="ad-image"
        >
      </div>
      <div class="ad-right">
        <div class="ad-content-wrapper">
          <h1 class="main-title">学习编程哪家强</h1>
          <h2 class="sub-title">山东高合杨师强</h2>
          <p class="desc">专注Java开发 / 前后端分离 / 项目实战 / 就业指导</p>
          <div class="tag-group">
            <span class="tag">高合教育</span>
            <span class="tag">实战教学</span>
            <span class="tag">名师带队</span>
          </div>
        </div>
        <div class="btn-group">
          <el-button type="primary" size="default" @click="visible = false">
            已了解，关闭
          </el-button>
        </div>
      </div>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: "AdPopup",
  data() {
    return {
      visible: true
    };
  },
  mounted() {
    this.visible = true;
  }
};
</script>

<style lang="scss" scoped>
::v-deep .yang-ad-dialog {
  .el-dialog {
    border-radius: 24px;
    overflow: hidden;
    background: linear-gradient(135deg, #4f46e5 0%, #7c3aed 100%);
    box-shadow: 0 25px 60px rgba(0, 0, 0, 0.25);
    border: none;
  }
  .el-dialog__body {
    padding: 0;
  }
}

.ad-container {
  display: flex;
  width: 100%;
  min-height: 460px;
  background: linear-gradient(135deg, #4f46e5 0%, #7c3aed 50%, #c026d3 100%);
  color: #fff;
}

.ad-left {
  width: 42%;
  height: 460px;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  box-sizing: border-box;
}

.ad-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 16px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
}

.ad-right {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 40px 50px;
  text-align: center;
}

.ad-content-wrapper {
  margin-bottom: 40px;
}

.main-title {
  font-size: 36px;
  font-weight: 800;
  margin: 0 0 12px 0;
  text-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  letter-spacing: 2px;
}

.sub-title {
  font-size: 32px;
  font-weight: 700;
  color: #fef3c7;
  margin: 0 0 20px 0;
  text-shadow: 0 3px 6px rgba(0, 0, 0, 0.15);
  letter-spacing: 1px;
}

.desc {
  font-size: 16px;
  opacity: 0.95;
  margin: 0 0 28px 0;
}

.tag-group {
  display: flex;
  gap: 12px;
  justify-content: center;

  .tag {
    padding: 6px 14px;
    background: rgba(255, 255, 255, 0.15);
    border-radius: 20px;
    font-size: 14px;
    font-weight: 500;
    backdrop-filter: blur(10px);
  }
}

.btn-group {
  margin-top: 10px;
}
</style>
