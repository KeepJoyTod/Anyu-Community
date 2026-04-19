import AdPopup from './AdPopup'

const components = {
  // ...其他组件
  AdPopup
}

export default {
  install(Vue) {
    Object.keys(components).forEach((key) => {
      Vue.component(key, components[key])
    })
  }
}
