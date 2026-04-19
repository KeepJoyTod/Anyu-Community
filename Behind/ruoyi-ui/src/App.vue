<!--<template>-->
<!--  <div id="app">-->
<!--    <router-view />-->
<!--    <theme-picker />-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--import ThemePicker from "@/components/ThemePicker"-->

<!--export default {-->
<!--  name: "App",-->
<!--  components: { ThemePicker }-->
<!--}-->
<!--</script>-->
<!--<style scoped>-->
<!--#app .theme-picker {-->
<!--  display: none;-->
<!--}-->
<!--</style>-->
<template>
  <div id="app">
    <router-view />
    <AdPopup />
  </div>
</template>

<script>
import AdPopup from "@/components/AdPopup";

export default {
  name: "App",
  components: {
    AdPopup
  }
};
</script>

