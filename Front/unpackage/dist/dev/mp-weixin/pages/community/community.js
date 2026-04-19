(global["webpackJsonp"] = global["webpackJsonp"] || []).push([["pages/community/community"],{

/***/ 64:
/*!***************************************************************************************!*\
  !*** D:/Java/projectku/安隅智能社区/Front/main.js?{"page":"pages%2Fcommunity%2Fcommunity"} ***!
  \***************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(wx, createPage) {

var _interopRequireDefault = __webpack_require__(/*! @babel/runtime/helpers/interopRequireDefault */ 4);
__webpack_require__(/*! uni-pages */ 26);
var _vue = _interopRequireDefault(__webpack_require__(/*! vue */ 25));
var _community = _interopRequireDefault(__webpack_require__(/*! ./pages/community/community.vue */ 65));
// @ts-ignore
wx.__webpack_require_UNI_MP_PLUGIN__ = __webpack_require__;
createPage(_community.default);
/* WEBPACK VAR INJECTION */}.call(this, __webpack_require__(/*! ./node_modules/@dcloudio/uni-mp-weixin/dist/wx.js */ 1)["default"], __webpack_require__(/*! ./node_modules/@dcloudio/uni-mp-weixin/dist/index.js */ 2)["createPage"]))

/***/ }),

/***/ 65:
/*!********************************************************************!*\
  !*** D:/Java/projectku/安隅智能社区/Front/pages/community/community.vue ***!
  \********************************************************************/
/*! no static exports found */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _community_vue_vue_type_template_id_2c27ccda_scoped_true___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./community.vue?vue&type=template&id=2c27ccda&scoped=true& */ 66);
/* harmony import */ var _community_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./community.vue?vue&type=script&lang=js& */ 68);
/* harmony reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in _community_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__) if(["default"].indexOf(__WEBPACK_IMPORT_KEY__) < 0) (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return _community_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__[key]; }) }(__WEBPACK_IMPORT_KEY__));
/* harmony import */ var _community_vue_vue_type_style_index_0_id_2c27ccda_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./community.vue?vue&type=style&index=0&id=2c27ccda&scoped=true&lang=css& */ 72);
/* harmony import */ var _LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_runtime_componentNormalizer_js__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/runtime/componentNormalizer.js */ 32);

var renderjs





/* normalize component */

var component = Object(_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_runtime_componentNormalizer_js__WEBPACK_IMPORTED_MODULE_3__["default"])(
  _community_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__["default"],
  _community_vue_vue_type_template_id_2c27ccda_scoped_true___WEBPACK_IMPORTED_MODULE_0__["render"],
  _community_vue_vue_type_template_id_2c27ccda_scoped_true___WEBPACK_IMPORTED_MODULE_0__["staticRenderFns"],
  false,
  null,
  "2c27ccda",
  null,
  false,
  _community_vue_vue_type_template_id_2c27ccda_scoped_true___WEBPACK_IMPORTED_MODULE_0__["components"],
  renderjs
)

component.options.__file = "pages/community/community.vue"
/* harmony default export */ __webpack_exports__["default"] = (component.exports);

/***/ }),

/***/ 66:
/*!***************************************************************************************************************!*\
  !*** D:/Java/projectku/安隅智能社区/Front/pages/community/community.vue?vue&type=template&id=2c27ccda&scoped=true& ***!
  \***************************************************************************************************************/
/*! exports provided: render, staticRenderFns, recyclableRender, components */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_17_0_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_community_vue_vue_type_template_id_2c27ccda_scoped_true___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/templateLoader.js??vue-loader-options!../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--17-0!../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/webpack-uni-mp-loader/lib/template.js!../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-uni-app-loader/page-meta.js!../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./community.vue?vue&type=template&id=2c27ccda&scoped=true& */ 67);
/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "render", function() { return _LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_17_0_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_community_vue_vue_type_template_id_2c27ccda_scoped_true___WEBPACK_IMPORTED_MODULE_0__["render"]; });

/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "staticRenderFns", function() { return _LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_17_0_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_community_vue_vue_type_template_id_2c27ccda_scoped_true___WEBPACK_IMPORTED_MODULE_0__["staticRenderFns"]; });

/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "recyclableRender", function() { return _LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_17_0_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_community_vue_vue_type_template_id_2c27ccda_scoped_true___WEBPACK_IMPORTED_MODULE_0__["recyclableRender"]; });

/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "components", function() { return _LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_17_0_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_community_vue_vue_type_template_id_2c27ccda_scoped_true___WEBPACK_IMPORTED_MODULE_0__["components"]; });



/***/ }),

/***/ 67:
/*!***************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/templateLoader.js??vue-loader-options!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--17-0!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/template.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-uni-app-loader/page-meta.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!D:/Java/projectku/安隅智能社区/Front/pages/community/community.vue?vue&type=template&id=2c27ccda&scoped=true& ***!
  \***************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! exports provided: render, staticRenderFns, recyclableRender, components */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "render", function() { return render; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "staticRenderFns", function() { return staticRenderFns; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "recyclableRender", function() { return recyclableRender; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "components", function() { return components; });
var components
var render = function () {
  var _vm = this
  var _h = _vm.$createElement
  var _c = _vm._self._c || _h
  var g0 = _vm.currentTab === 0 && !_vm.loading ? _vm.sharePosts.length : null
  var l1 =
    _vm.currentTab === 0 && !_vm.loading && !(g0 === 0)
      ? _vm.__map(_vm.sharePosts, function (post, idx) {
          var $orig = _vm.__get_orig(post)
          var g1 = post.nickname.charAt(0)
          var g2 = post.commentsList && post.commentsList.length
          var l0 = g2 ? post.commentsList.slice(0, 3) : null
          var g3 = g2 ? post.commentsList.length : null
          var g4 = g2 && g3 > 3 ? post.commentsList.length : null
          return {
            $orig: $orig,
            g1: g1,
            g2: g2,
            l0: l0,
            g3: g3,
            g4: g4,
          }
        })
      : null
  var g5 =
    !(_vm.currentTab === 0) &&
    _vm.currentTab === 1 &&
    !_vm.loading &&
    _vm.activityData.campus.time
      ? _vm.activityData.campus.time.slice(0, 10)
      : null
  var g6 =
    !(_vm.currentTab === 0) &&
    _vm.currentTab === 1 &&
    !_vm.loading &&
    _vm.activityData.elderly.time
      ? _vm.activityData.elderly.time.slice(0, 10)
      : null
  var g7 =
    !(_vm.currentTab === 0) &&
    _vm.currentTab === 1 &&
    !_vm.loading &&
    _vm.activityData.park.time
      ? _vm.activityData.park.time.slice(0, 10)
      : null
  var g8 =
    !(_vm.currentTab === 0) &&
    _vm.currentTab === 1 &&
    !_vm.loading &&
    _vm.activityData.doctor.time
      ? _vm.activityData.doctor.time.slice(0, 10)
      : null
  var g9 =
    !(_vm.currentTab === 0) &&
    _vm.currentTab === 1 &&
    !_vm.loading &&
    _vm.activityData.trash.time
      ? _vm.activityData.trash.time.slice(0, 10)
      : null
  var g10 =
    !(_vm.currentTab === 0) &&
    !(_vm.currentTab === 1) &&
    _vm.currentTab === 2 &&
    !_vm.loading
      ? _vm.donateList.length
      : null
  var l2 =
    !(_vm.currentTab === 0) &&
    !(_vm.currentTab === 1) &&
    _vm.currentTab === 2 &&
    !_vm.loading &&
    !(g10 === 0)
      ? _vm.__map(_vm.donateList, function (item, index) {
          var $orig = _vm.__get_orig(item)
          var m0 = _vm.getDonateImage(item, index)
          var m1 = _vm.getRate(item)
          var m2 = m1 ? _vm.getRate(item) : null
          var m3 = _vm.getRate(item)
          var m4 = m3 ? _vm.getRate(item) : null
          var g11 =
            !m3 && item.nowMoney && item.targetMoney
              ? ((item.nowMoney / item.targetMoney) * 100).toFixed(1)
              : null
          return {
            $orig: $orig,
            m0: m0,
            m1: m1,
            m2: m2,
            m3: m3,
            m4: m4,
            g11: g11,
          }
        })
      : null
  var g12 = _vm.showPublishDialog ? _vm.publishContent.length : null
  var g13 = _vm.showPublishDialog ? _vm.publishContent.trim() : null
  if (!_vm._isMounted) {
    _vm.e0 = function ($event, tag) {
      var _temp = arguments[arguments.length - 1].currentTarget.dataset,
        _temp2 = _temp.eventParams || _temp["event-params"],
        tag = _temp2.tag
      var _temp, _temp2
      _vm.selectedTag = tag
    }
  }
  _vm.$mp.data = Object.assign(
    {},
    {
      $root: {
        g0: g0,
        l1: l1,
        g5: g5,
        g6: g6,
        g7: g7,
        g8: g8,
        g9: g9,
        g10: g10,
        l2: l2,
        g12: g12,
        g13: g13,
      },
    }
  )
}
var recyclableRender = false
var staticRenderFns = []
render._withStripped = true



/***/ }),

/***/ 68:
/*!*********************************************************************************************!*\
  !*** D:/Java/projectku/安隅智能社区/Front/pages/community/community.vue?vue&type=script&lang=js& ***!
  \*********************************************************************************************/
/*! no static exports found */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_13_1_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_community_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/babel-loader/lib!../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--13-1!../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/webpack-uni-mp-loader/lib/script.js!../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./community.vue?vue&type=script&lang=js& */ 69);
/* harmony import */ var _LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_13_1_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_community_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_13_1_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_community_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__);
/* harmony reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in _LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_13_1_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_community_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__) if(["default"].indexOf(__WEBPACK_IMPORT_KEY__) < 0) (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return _LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_13_1_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_community_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__[key]; }) }(__WEBPACK_IMPORT_KEY__));
 /* harmony default export */ __webpack_exports__["default"] = (_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_13_1_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_community_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0___default.a); 

/***/ }),

/***/ 69:
/*!****************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/babel-loader/lib!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--13-1!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/script.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!D:/Java/projectku/安隅智能社区/Front/pages/community/community.vue?vue&type=script&lang=js& ***!
  \****************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(uni) {

var _interopRequireDefault = __webpack_require__(/*! @babel/runtime/helpers/interopRequireDefault */ 4);
Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = void 0;
var _regenerator = _interopRequireDefault(__webpack_require__(/*! @babel/runtime/regenerator */ 57));
var _asyncToGenerator2 = _interopRequireDefault(__webpack_require__(/*! @babel/runtime/helpers/asyncToGenerator */ 59));
var _defineProperty2 = _interopRequireDefault(__webpack_require__(/*! @babel/runtime/helpers/defineProperty */ 11));
var _index = __webpack_require__(/*! ../../api/index.js */ 70);
function ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); enumerableOnly && (symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; })), keys.push.apply(keys, symbols); } return keys; }
function _objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = null != arguments[i] ? arguments[i] : {}; i % 2 ? ownKeys(Object(source), !0).forEach(function (key) { (0, _defineProperty2.default)(target, key, source[key]); }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)) : ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } return target; }
var _default = {
  data: function data() {
    var _ref;
    return _ref = {
      currentTab: 0,
      tabs: [{
        name: '日常分享',
        type: 'share'
      }, {
        name: '志愿者',
        type: 'volunteer'
      }, {
        name: '爱心捐款',
        type: 'donate'
      }, {
        name: '积分商城',
        type: 'points'
      }],
      posts: [],
      activityData: {
        campus: {
          id: 1,
          title: "加载中...",
          time: "2025-01-01 00:00:00",
          address: "",
          content: "",
          contact: "王社工 13800138000",
          count: 0,
          max: 1,
          imageUrl: "/static/activity01.jpg"
        },
        elderly: {
          id: 2,
          title: "加载中...",
          time: "2025-01-01 00:00:00",
          address: "",
          content: "",
          contact: "王社工 13800138000",
          count: 0,
          max: 1,
          imageUrl: "/static/activity02.webp"
        },
        park: {
          id: 3,
          title: "加载中...",
          time: "2025-01-01 00:00:00",
          address: "",
          content: "",
          contact: "王社工 13800138000",
          count: 0,
          max: 1,
          imageUrl: "/static/activity03.jpg"
        },
        doctor: {
          id: 4,
          title: "加载中...",
          time: "2025-01-01 00:00:00",
          address: "",
          content: "",
          contact: "王社工 13800138000",
          count: 0,
          max: 1,
          imageUrl: "/static/activity04.jpg"
        },
        trash: {
          id: 5,
          title: "加载中...",
          time: "2025-01-01 00:00:00",
          address: "",
          content: "",
          contact: "王社工 13800138000",
          count: 0,
          max: 1,
          imageUrl: "/static/activity05.webp"
        },
        volunteer: {
          id: 6,
          title: "加载中...",
          time: "2025-01-01 00:00:00",
          address: "",
          content: "",
          contact: "王社工 13800138000",
          count: 0,
          max: 1,
          imageUrl: "/static/activity_show.jpg"
        },
        good_deed: {
          id: 7,
          title: "加载中...",
          time: "2025-01-01 00:00:00",
          address: "",
          content: "",
          contact: "王社工 13800138000",
          count: 0,
          max: 1,
          imageUrl: "/static/activity_tree.jpg"
        }
      },
      donateList: [],
      donateImgErrors: {},
      loading: false,
      sharePosts: [],
      volunteerStats: {
        total: 156,
        activeToday: 43,
        totalHours: 2840
      },
      volunteerPosts: [{
        nickname: '热心小陈',
        time: '昨天 14:30',
        content: '参加了社区周末的环保清洁活动，捡了满满两大袋垃圾，虽然累但很有意义！希望大家一起爱护环境~',
        likes: 32,
        dislikes: 0,
        points: 8,
        liked: false,
        disliked: false,
        avatarColor: '#4caf50',
        duration: 12,
        times: 5,
        bio: '环保爱好者，喜欢户外运动，希望能让社区更美好。'
      }, {
        nickname: '猫咪守护者',
        time: '昨天 09:15',
        content: '流浪猫绝育计划今天顺利完成第一只猫咪，感谢两位兽医志愿者的付出！还需要更多帮手，一起加入吧。',
        likes: 56,
        dislikes: 2,
        points: 12,
        liked: false,
        disliked: false,
        avatarColor: '#ff9800',
        duration: 8,
        times: 3,
        bio: '动物保护志愿者，救助流浪猫狗是我的使命。'
      }]
    }, (0, _defineProperty2.default)(_ref, "donateList", []), (0, _defineProperty2.default)(_ref, "projectList", [{
      id: 1,
      cover: "/static/donation/clearing.jpg",
      status: "运行中",
      location: "北京科技大学天津学院",
      title: '"爱环保"垃圾清除公益行动',
      current: 128,
      target: 200,
      deadline: "2026-03-18"
    }, {
      id: 2,
      cover: "/static/donation/job.jpg",
      status: "运行中",
      location: "恒基中心1座",
      title: "职场加油站志愿服务项目",
      current: 342,
      target: 1000,
      deadline: "2026-03-06"
    }, {
      id: 3,
      cover: "/static/donation/elderly.jpg",
      status: "运行中",
      location: "朝阳区养老院",
      title: "关爱空巢老人陪伴计划",
      current: 45,
      target: 50,
      deadline: "2026-04-01"
    }, {
      id: 4,
      cover: "/static/donation/student.jpg",
      status: "运行中",
      location: "河北涞源希望小学",
      title: "山区儿童助学物资捐赠",
      current: 89,
      target: 200,
      deadline: "2026-03-25"
    }]), (0, _defineProperty2.default)(_ref, "userPoints", 0), (0, _defineProperty2.default)(_ref, "noticeList", ['3号楼张阿姨报名了关爱老人活动', '5分钟前，李叔叔捐了100元助力山区儿童', '10分钟前，王同学成为环保义演第一个报名者', '2号楼王奶奶捐了50元给流浪动物救助', '7号楼的小明报名了周末亲子手工分享', '1号楼的刘叔叔捐了200元给紧急救灾储备', '5号楼的张阿姨分享了她的公益日常', '4号楼的李同学成为了社区环保志愿者']), (0, _defineProperty2.default)(_ref, "topVolunteers", [{
      name: '张阿姨',
      avatar: '/static/美女1.png',
      desc: '报名3次活动'
    }, {
      name: '李叔叔',
      avatar: '/static/美女2.png',
      desc: '报名2次活动'
    }, {
      name: '王同学',
      avatar: '/static/美女3.png',
      desc: '分享2次公益'
    }]), (0, _defineProperty2.default)(_ref, "scrollNums", {}), (0, _defineProperty2.default)(_ref, "topDonators", [{
      name: '张阿姨',
      avatar: '/static/美女1.png',
      desc: '捐款200元'
    }, {
      name: '李叔叔',
      avatar: '/static/美女2.png',
      desc: '捐款150元'
    }, {
      name: '王奶奶',
      avatar: '/static/美女3.png',
      desc: '捐款100元'
    }, {
      name: '刘叔叔',
      avatar: '/static/美女4.png',
      desc: '捐款80元'
    }]), (0, _defineProperty2.default)(_ref, "showHearts", false), (0, _defineProperty2.default)(_ref, "hearts", []), (0, _defineProperty2.default)(_ref, "showPublishDialog", false), (0, _defineProperty2.default)(_ref, "publishContent", ''), (0, _defineProperty2.default)(_ref, "selectedTag", '#日常分享'), (0, _defineProperty2.default)(_ref, "publishTags", ['#日常分享', '#好人好事', '#爱心捐款']), _ref;
  },
  onLoad: function onLoad(options) {
    var _this = this;
    this.fetchPosts();
    this.loadActivityFromBackend();
    this.loadDonateData();
    if (options && options.openPublish === 'true') {
      setTimeout(function () {
        _this.showPublishModal();
      }, 500);
    }
    var that = this;
    setTimeout(function () {
      if (that.activityData) {
        var activities = [that.activityData.campus, that.activityData.elderly, that.activityData.park, that.activityData.doctor, that.activityData.trash];
        activities.forEach(function (item, index) {
          if (item && item.count) {
            that.scrollNumber("vol_".concat(index), item.count);
          }
        });
      }
    }, 300);
  },
  computed: {
    totalPostsCount: function totalPostsCount() {
      return this.sharePosts.length + this.volunteerPosts.length;
    }
  },
  mounted: function mounted() {
    this.fetchPoints();
  },
  onShow: function onShow() {
    var _this2 = this;
    if (getApp().globalData && getApp().globalData.pendingCommunityTab !== undefined) {
      var targetTab = getApp().globalData.pendingCommunityTab;
      this.currentTab = targetTab;
      delete getApp().globalData.pendingCommunityTab;
    }
    setTimeout(function () {
      _this2.loadActivityFromBackend();
      _this2.loadPostsFromBackend();
    }, 100);
  },
  watch: {
    donateList: {
      handler: function handler(newVal) {
        if (newVal && newVal.length > 0) {
          var that = this;
          newVal.forEach(function (item, index) {
            var realNow = item.nowMoney;
            var rateNum = that.getRate(item);
            if (realNow === 0 && rateNum > 0 && item.targetMoney) {
              realNow = Math.floor(item.targetMoney * rateNum / 100);
            }
            if (realNow > 0) {
              that.scrollNumber("donate_".concat(index), realNow);
            }
          });
        }
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    getRate: function getRate(item) {
      if (!item.rate) return 0;
      if (typeof item.rate === 'string') {
        return parseFloat(item.rate.replace('%', ''));
      }
      return item.rate;
    },
    scrollNumber: function scrollNumber(key, targetNum) {
      var duration = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : 1000;
      var that = this;
      var step = targetNum / (duration / 16);
      var current = 0;
      var timer = setInterval(function () {
        current += step;
        if (current >= targetNum) {
          current = targetNum;
          clearInterval(timer);
        }
        that.$set(that.scrollNums, key, Math.floor(current));
      }, 16);
    },
    triggerHeartRain: function triggerHeartRain() {
      var that = this;
      that.showHearts = true;
      var hearts = [];
      for (var i = 0; i < 20; i++) {
        hearts.push({
          left: Math.random() * 600,
          delay: Math.random() * 1
        });
      }
      that.hearts = hearts;
      setTimeout(function () {
        that.showHearts = false;
      }, 2500);
    },
    updateActivityCount: function updateActivityCount(type, newCount) {
      if (this.activityData[type]) {
        this.activityData[type].count = newCount;
        var indexMap = {
          campus: 0,
          elderly: 1,
          park: 2,
          doctor: 3,
          trash: 4,
          volunteer: 0,
          good_deed: 1
        };
        var index = indexMap[type];
        if (index !== undefined) {
          this.scrollNumber("vol_".concat(index), newCount, 500);
        }
        this.$forceUpdate();
      }
    },
    handleGoCampus: function handleGoCampus() {
      this.goTopic('campus');
      this.triggerHeartRain();
    },
    handleGoElderly: function handleGoElderly() {
      this.goTopic('elderly');
      this.triggerHeartRain();
    },
    handleGoPark: function handleGoPark() {
      this.goTopic('park');
      this.triggerHeartRain();
    },
    handleGoDoctor: function handleGoDoctor() {
      this.goTopic('doctor');
      this.triggerHeartRain();
    },
    handleGoTrash: function handleGoTrash() {
      this.goTopic('trash');
      this.triggerHeartRain();
    },
    fetchPosts: function fetchPosts() {
      var _this3 = this;
      return (0, _asyncToGenerator2.default)( /*#__PURE__*/_regenerator.default.mark(function _callee() {
        var res, posts;
        return _regenerator.default.wrap(function _callee$(_context) {
          while (1) {
            switch (_context.prev = _context.next) {
              case 0:
                _this3.loading = true;
                _context.prev = 1;
                _context.next = 4;
                return _index.communityApi.getPostList({
                  pageNum: 1,
                  pageSize: 10
                });
              case 4:
                res = _context.sent;
                if (res.code === 200) {
                  posts = res.data || [];
                  _this3.sharePosts = posts.map(function (post) {
                    return {
                      id: post.postId,
                      avatarColor: _this3.getAvatarColor(post.userId || post.postId),
                      nickname: post.userName || '用户' + post.postId,
                      time: post.createTime || new Date().toLocaleString(),
                      tag: post.tag || '#日常分享',
                      tagClass: _this3.getTagClass(post.tag || '#日常分享'),
                      content: post.content,
                      likes: post.likes || 0,
                      dislikes: post.dislikes || 0,
                      comments: post.comments ? post.comments.length : 0,
                      commentsList: post.comments ? post.comments.map(function (comment) {
                        return {
                          commentId: comment.commentId,
                          user: comment.userName,
                          content: comment.content,
                          time: comment.createTime
                        };
                      }) : [],
                      points: post.points || 5,
                      liked: false,
                      disliked: false,
                      commented: false
                    };
                  });
                } else {
                  uni.showToast({
                    title: res.msg || '获取失败',
                    icon: 'none'
                  });
                }
                _context.next = 12;
                break;
              case 8:
                _context.prev = 8;
                _context.t0 = _context["catch"](1);
                console.error(_context.t0);
                uni.showToast({
                  title: '获取数据失败',
                  icon: 'none'
                });
              case 12:
                _context.prev = 12;
                _this3.loading = false;
                return _context.finish(12);
              case 15:
              case "end":
                return _context.stop();
            }
          }
        }, _callee, null, [[1, 8, 12, 15]]);
      }))();
    },
    fetchBills: function fetchBills() {
      var _this4 = this;
      return (0, _asyncToGenerator2.default)( /*#__PURE__*/_regenerator.default.mark(function _callee2() {
        var res;
        return _regenerator.default.wrap(function _callee2$(_context2) {
          while (1) {
            switch (_context2.prev = _context2.next) {
              case 0:
                _context2.prev = 0;
                _context2.next = 3;
                return _index.pointsApi.getBills();
              case 3:
                res = _context2.sent;
                if (res.code === 200) {
                  _this4.bills = res.data.map(function (bill) {
                    var icon = '💧';
                    if (bill.type === 'water') icon = '💧';else if (bill.type === 'electricity') icon = '⚡';else if (bill.type === 'property') icon = '🏠';
                    return _objectSpread(_objectSpread({}, bill), {}, {
                      icon: icon
                    });
                  });
                } else {
                  uni.showToast({
                    title: '获取账单失败',
                    icon: 'none'
                  });
                }
                _context2.next = 10;
                break;
              case 7:
                _context2.prev = 7;
                _context2.t0 = _context2["catch"](0);
                console.error(_context2.t0);
              case 10:
              case "end":
                return _context2.stop();
            }
          }
        }, _callee2, null, [[0, 7]]);
      }))();
    },
    goToMall: function goToMall() {
      uni.navigateTo({
        url: '/pages/mall/mall'
      });
    },
    goToTaskCenter: function goToTaskCenter() {
      console.log('点击了任务中心');
      uni.navigateTo({
        url: '/pages/mall/task-center',
        success: function success() {
          console.log('跳转成功');
        },
        fail: function fail(err) {
          console.error('跳转失败:', err);
          uni.showToast({
            title: '页面跳转失败',
            icon: 'none'
          });
        }
      });
    },
    loadActivityFromBackend: function loadActivityFromBackend() {
      var _this5 = this;
      if (this.loading) return;
      this.loading = true;
      uni.request({
        url: "http://127.0.0.1:8080/community/activity/list",
        method: "GET",
        header: {
          'Content-Type': 'application/json'
        },
        success: function success(res) {
          if (res.data.code === 200) {
            var dataList = res.data.data || [];
            var idToType = {
              1: 'campus',
              2: 'elderly',
              3: 'park',
              4: 'doctor',
              5: 'trash',
              6: 'volunteer',
              7: 'good_deed'
            };
            dataList.forEach(function (item) {
              var type = idToType[item.activityId];
              if (!type) return;
              _this5.activityData[type] = {
                id: item.activityId,
                title: item.activityTitle || '暂无标题',
                time: item.activityTime || '暂无时间',
                address: item.activityLocation || '暂无地址',
                content: item.activityContent || '暂无内容',
                contact: "王社工 13800138000",
                count: item.currentParticipants || 0,
                max: item.maxParticipants || 0,
                imageUrl: _this5.getImageById(item.activityId)
              };
            });
          } else {
            uni.showToast({
              title: '数据加载失败',
              icon: 'none'
            });
          }
        },
        fail: function fail() {
          console.log("请求失败");
          uni.showToast({
            title: '网络错误，请重试',
            icon: 'none'
          });
        },
        complete: function complete() {
          _this5.loading = false;
        }
      });
    },
    loadPostsFromBackend: function loadPostsFromBackend() {
      var _this6 = this;
      if (this.loading) return;
      this.loading = true;
      uni.request({
        url: "http://127.0.0.1:8080/community/share/list",
        method: "GET",
        header: {
          'Content-Type': 'application/json'
        },
        success: function success(res) {
          if (res.data.code === 200) {
            var posts = res.data.data || [];
            _this6.posts = posts.map(function (post) {
              return {
                id: post.postId,
                avatarColor: _this6.getAvatarColor(post.userId || post.postId),
                nickname: post.userName || '用户' + post.postId,
                time: post.createTime || new Date().toLocaleString(),
                tag: post.tag || '#日常分享',
                tagClass: _this6.getTagClass(post.tag || '#日常分享'),
                content: post.content,
                likes: post.likes || 0,
                dislikes: post.dislikes || 0,
                comments: post.comments ? post.comments.length : 0,
                commentsList: post.comments ? post.comments.map(function (comment) {
                  return {
                    commentId: comment.commentId,
                    user: comment.userName,
                    content: comment.content,
                    time: comment.createTime
                  };
                }) : [],
                points: post.points || 5,
                liked: false,
                disliked: false,
                commented: false
              };
            });
          } else {
            uni.showToast({
              title: '帖子加载失败',
              icon: 'none'
            });
          }
        },
        fail: function fail() {
          uni.showToast({
            title: '网络错误，请重试',
            icon: 'none'
          });
        },
        complete: function complete() {
          _this6.loading = false;
        }
      });
    },
    loadDonateData: function loadDonateData() {
      var _this7 = this;
      if (this.loading) return;
      this.loading = true;
      uni.request({
        url: "http://127.0.0.1:8080/community/donation/list",
        method: "GET",
        header: {
          'Content-Type': 'application/json'
        },
        success: function success(res) {
          if (res.data.code === 200) {
            _this7.donateList = res.data.data || [];
          } else {
            uni.showToast({
              title: '捐款数据加载失败',
              icon: 'none'
            });
          }
        },
        fail: function fail() {
          console.log("捐款请求失败");
          uni.showToast({
            title: '网络错误，请重试',
            icon: 'none'
          });
        },
        complete: function complete() {
          _this7.loading = false;
        }
      });
    },
    goDonateDetail: function goDonateDetail(item) {
      if (!item) return;
      uni.navigateTo({
        url: "/pages/community/donate-detail?donate=".concat(encodeURIComponent(JSON.stringify(item)))
      });
    },
    goTopic: function goTopic(type) {
      var act = this.activityData[type];
      if (!act || act.title === '加载中...') {
        uni.showToast({
          title: '活动数据加载中，请稍后',
          icon: 'none'
        });
        return;
      }
      uni.navigateTo({
        url: "/pages/community/topic-detail?activity=".concat(encodeURIComponent(JSON.stringify(act)))
      });
    },
    goCampus: function goCampus() {
      this.goTopic('campus');
    },
    goElderly: function goElderly() {
      this.goTopic('elderly');
    },
    goPark: function goPark() {
      this.goTopic('park');
    },
    goDoctor: function goDoctor() {
      this.goTopic('doctor');
    },
    goTrash: function goTrash() {
      this.goTopic('trash');
    },
    fetchPoints: function fetchPoints() {
      var _this8 = this;
      return (0, _asyncToGenerator2.default)( /*#__PURE__*/_regenerator.default.mark(function _callee3() {
        var userInfo, res;
        return _regenerator.default.wrap(function _callee3$(_context3) {
          while (1) {
            switch (_context3.prev = _context3.next) {
              case 0:
                userInfo = uni.getStorageSync('userInfo');
                if (!(userInfo && userInfo.userId)) {
                  _context3.next = 12;
                  break;
                }
                _context3.prev = 2;
                _context3.next = 5;
                return _index.pointsApi.getPoints(userInfo.userId);
              case 5:
                res = _context3.sent;
                if (res.code === 200) {
                  _this8.userPoints = res.data.availablePoints || 0;
                } else {
                  console.error('获取积分失败:', res.msg);
                }
                _context3.next = 12;
                break;
              case 9:
                _context3.prev = 9;
                _context3.t0 = _context3["catch"](2);
                console.error('获取积分异常:', _context3.t0);
              case 12:
              case "end":
                return _context3.stop();
            }
          }
        }, _callee3, null, [[2, 9]]);
      }))();
    },
    getTagClass: function getTagClass(tag) {
      if (tag === '#好人好事') return 'good';
      if (tag === '#日常分享') return 'share';
      if (tag === '#爱心捐款') return 'donate';
      return '';
    },
    getAvatarColor: function getAvatarColor(userId) {
      var colors = ['#6b8c42', '#2d8cf0', '#e67e22', '#4caf50', '#ff9800'];
      return colors[userId % colors.length];
    },
    getDonateImage: function getDonateImage(item, index) {
      var fallbacks = ['/static/help.jpg', '/static/animal.jpg', '/static/study.jpg', '/static/health.jpg', '/static/environment.jpg'];
      if (this.donateImgErrors[index] || !item.image) {
        return fallbacks[index % fallbacks.length];
      }
      if (item.image.startsWith('http')) {
        return item.image;
      }
      return 'http://127.0.0.1:8080' + item.image;
    },
    onDonateImgError: function onDonateImgError(index) {
      this.$set(this.donateImgErrors, index, true);
    },
    getImageById: function getImageById(id) {
      var map = {
        1: "/static/activity01.jpg",
        2: "/static/activity02.webp",
        3: "/static/activity03.jpg",
        4: "/static/activity04.jpg",
        5: "/static/activity05.webp",
        6: "/static/activity_show.jpg",
        7: "/static/activity_tree.jpg"
      };
      return map[id] || "/static/activity01.jpg";
    },
    switchTab: function switchTab(index) {
      if (index === 0 && this.sharePosts.length === 0) {
        this.fetchPosts();
      } else if (index === 1) {
        this.loadActivityFromBackend();
      } else if (index === 2 && this.donateList.length === 0) {
        this.loadDonateData();
      }
      this.currentTab = index;
    },
    likePost: function likePost(type, idx) {
      // 统一处理日常分享和志愿者页面的点赞逻辑
      var list = type === 'share' ? this.sharePosts : this.volunteerPosts;
      var post = list[idx];
      if (!post.liked) {
        post.likes++;
        post.liked = true;
        if (post.disliked) {
          post.dislikes--;
          post.disliked = false;
        }

        // 点赞加2积分
        var points = 2;
        uni.showToast({
          title: "+".concat(points, "\u79EF\u5206"),
          icon: 'none'
        });

        // 根据登录人的ID把积分加到数据库中
        var token = uni.getStorageSync('community_token');
        if (token) {
          uni.request({
            url: 'http://127.0.0.1:8080/home/points/add',
            method: 'POST',
            header: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + token
            },
            data: {
              points: points,
              reason: '点赞互动'
            },
            success: function success(res) {
              console.log('添加积分成功:', res.data);
            },
            fail: function fail(err) {
              console.error('添加积分失败:', err);
            }
          });
        }
      } else {
        post.likes--;
        post.liked = false;
        uni.showToast({
          title: '已取消点赞',
          icon: 'none'
        });
      }
    },
    dislikePost: function dislikePost(type, idx) {
      // 统一处理日常分享和志愿者页面的点踩逻辑
      var list = type === 'share' ? this.sharePosts : this.volunteerPosts;
      var post = list[idx];
      if (!post.disliked) {
        post.dislikes++;
        post.disliked = true;
        if (post.liked) {
          post.likes--;
          post.liked = false;
        }
        uni.showToast({
          title: '点踩成功',
          icon: 'none'
        });
      } else {
        post.dislikes--;
        post.disliked = false;
        uni.showToast({
          title: '已取消点踩',
          icon: 'none'
        });
      }
    },
    commentPost: function commentPost(type, idx) {
      var _this9 = this;
      return (0, _asyncToGenerator2.default)( /*#__PURE__*/_regenerator.default.mark(function _callee5() {
        var list, post;
        return _regenerator.default.wrap(function _callee5$(_context5) {
          while (1) {
            switch (_context5.prev = _context5.next) {
              case 0:
                list = type === 'share' ? _this9.sharePosts : _this9.volunteerPosts;
                post = list[idx];
                if (!post.commented) {
                  _context5.next = 5;
                  break;
                }
                uni.showToast({
                  title: '您已经评论过啦',
                  icon: 'none'
                });
                return _context5.abrupt("return");
              case 5:
                uni.showModal({
                  title: '发表评论',
                  editable: true,
                  placeholderText: '写下你的想法...',
                  success: function () {
                    var _success = (0, _asyncToGenerator2.default)( /*#__PURE__*/_regenerator.default.mark(function _callee4(modalRes) {
                      var content, res;
                      return _regenerator.default.wrap(function _callee4$(_context4) {
                        while (1) {
                          switch (_context4.prev = _context4.next) {
                            case 0:
                              if (!(modalRes.confirm && modalRes.content.trim())) {
                                _context4.next = 13;
                                break;
                              }
                              content = modalRes.content.trim();
                              _context4.prev = 2;
                              _context4.next = 5;
                              return _index.communityApi.addComment({
                                postId: post.id,
                                content: content
                              });
                            case 5:
                              res = _context4.sent;
                              if (res.code === 200) {
                                post.comments++;
                                post.commented = true;
                                post.commentsList.unshift({
                                  commentId: Date.now(),
                                  user: '我',
                                  // 从全局 store 获取真实昵称
                                  content: content,
                                  time: '刚刚'
                                });
                                // 评论奖励积分（示例）
                                _this9.points += 5;
                              }
                              _context4.next = 13;
                              break;
                            case 9:
                              _context4.prev = 9;
                              _context4.t0 = _context4["catch"](2);
                              console.error(_context4.t0);
                              uni.showToast({
                                title: '评论失败',
                                icon: 'none'
                              });
                            case 13:
                            case "end":
                              return _context4.stop();
                          }
                        }
                      }, _callee4, null, [[2, 9]]);
                    }));
                    function success(_x) {
                      return _success.apply(this, arguments);
                    }
                    return success;
                  }()
                });
              case 6:
              case "end":
                return _context5.stop();
            }
          }
        }, _callee5);
      }))();
    },
    // 显示发表分享弹窗
    showPublishModal: function showPublishModal() {
      // 检查用户是否登录
      var token = uni.getStorageSync('community_token');
      if (!token) {
        uni.showToast({
          title: '请先登录',
          icon: 'none'
        });
        return;
      }
      this.showPublishDialog = true;
    },
    // 关闭发表分享弹窗
    closePublishModal: function closePublishModal() {
      this.showPublishDialog = false;
      this.publishContent = '';
      this.selectedTag = '#日常分享';
    },
    // 封装 uni.request 为 Promise
    request: function request(options) {
      return new Promise(function (resolve, reject) {
        uni.request(_objectSpread(_objectSpread({}, options), {}, {
          success: function success(res) {
            resolve(res);
          },
          fail: function fail(err) {
            reject(err);
          }
        }));
      });
    },
    // 提交发表分享
    submitPublish: function submitPublish() {
      var _this10 = this;
      return (0, _asyncToGenerator2.default)( /*#__PURE__*/_regenerator.default.mark(function _callee6() {
        var token, publishRes, taskRes;
        return _regenerator.default.wrap(function _callee6$(_context6) {
          while (1) {
            switch (_context6.prev = _context6.next) {
              case 0:
                if (_this10.publishContent.trim()) {
                  _context6.next = 3;
                  break;
                }
                uni.showToast({
                  title: '请输入分享内容',
                  icon: 'none'
                });
                return _context6.abrupt("return");
              case 3:
                token = uni.getStorageSync('community_token');
                uni.showLoading({
                  title: '发布中...'
                });
                _context6.prev = 5;
                _context6.next = 8;
                return _this10.request({
                  url: 'http://127.0.0.1:8080/community/post',
                  method: 'POST',
                  data: {
                    content: _this10.publishContent.trim(),
                    tag: _this10.selectedTag
                  },
                  header: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                  }
                });
              case 8:
                publishRes = _context6.sent;
                if (!(publishRes.data.code === 200)) {
                  _context6.next = 19;
                  break;
                }
                _context6.next = 12;
                return _this10.request({
                  url: 'http://127.0.0.1:8080/community/task/complete/post',
                  method: 'POST',
                  header: {
                    'Authorization': 'Bearer ' + token
                  }
                });
              case 12:
                taskRes = _context6.sent;
                // 3. 显示发布成功和积分到账弹窗
                uni.showToast({
                  title: '发布成功！',
                  icon: 'success'
                });

                // 显示积分到账弹窗
                if (taskRes.data.code === 200 && taskRes.data.data) {
                  _this10.showPointsModal(taskRes.data.data.points || 10);
                }

                // 4. 关闭弹窗并刷新列表
                _this10.closePublishModal();
                _this10.fetchPosts();
                _context6.next = 20;
                break;
              case 19:
                uni.showToast({
                  title: publishRes.data.msg || '发布失败',
                  icon: 'none'
                });
              case 20:
                _context6.next = 26;
                break;
              case 22:
                _context6.prev = 22;
                _context6.t0 = _context6["catch"](5);
                console.error('发布失败:', _context6.t0);
                uni.showToast({
                  title: '发布失败，请重试',
                  icon: 'none'
                });
              case 26:
                _context6.prev = 26;
                uni.hideLoading();
                return _context6.finish(26);
              case 29:
              case "end":
                return _context6.stop();
            }
          }
        }, _callee6, null, [[5, 22, 26, 29]]);
      }))();
    },
    // 显示积分到账弹窗
    showPointsModal: function showPointsModal(points) {
      // 显示积分到账弹窗
      var modal = {
        icon: '🎉',
        title: '任务完成！',
        points: points,
        content: '已发放至您的账户',
        buttonText: '知道啦'
      };

      // 使用 uni.showModal 显示弹窗
      uni.showModal({
        title: modal.title,
        content: "".concat(modal.icon, "\n+").concat(modal.points, " \u79EF\u5206\n").concat(modal.content),
        showCancel: false,
        confirmText: modal.buttonText,
        success: function success(res) {
          if (res.confirm) {
            console.log('用户点击了知道啦');
          }
        }
      });
    }
  }
};
exports.default = _default;
/* WEBPACK VAR INJECTION */}.call(this, __webpack_require__(/*! ./node_modules/@dcloudio/uni-mp-weixin/dist/index.js */ 2)["default"]))

/***/ }),

/***/ 72:
/*!*****************************************************************************************************************************!*\
  !*** D:/Java/projectku/安隅智能社区/Front/pages/community/community.vue?vue&type=style&index=0&id=2c27ccda&scoped=true&lang=css& ***!
  \*****************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_6_oneOf_1_1_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_2_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_community_vue_vue_type_style_index_0_id_2c27ccda_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/mini-css-extract-plugin/dist/loader.js??ref--6-oneOf-1-0!../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/css-loader/dist/cjs.js??ref--6-oneOf-1-1!../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/stylePostLoader.js!../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--6-oneOf-1-2!../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/postcss-loader/src??ref--6-oneOf-1-3!../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!../../../../../../LeStoreDownload/HBuilderX.4.87.2025121004/HBuilderX/plugins/uniapp-cli/node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./community.vue?vue&type=style&index=0&id=2c27ccda&scoped=true&lang=css& */ 73);
/* harmony import */ var _LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_6_oneOf_1_1_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_2_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_community_vue_vue_type_style_index_0_id_2c27ccda_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_6_oneOf_1_1_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_2_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_community_vue_vue_type_style_index_0_id_2c27ccda_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_0__);
/* harmony reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in _LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_6_oneOf_1_1_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_2_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_community_vue_vue_type_style_index_0_id_2c27ccda_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_0__) if(["default"].indexOf(__WEBPACK_IMPORT_KEY__) < 0) (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return _LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_6_oneOf_1_1_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_2_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_community_vue_vue_type_style_index_0_id_2c27ccda_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_0__[key]; }) }(__WEBPACK_IMPORT_KEY__));
 /* harmony default export */ __webpack_exports__["default"] = (_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_6_oneOf_1_1_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_2_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_LeStoreDownload_HBuilderX_4_87_2025121004_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_community_vue_vue_type_style_index_0_id_2c27ccda_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_0___default.a); 

/***/ }),

/***/ 73:
/*!*********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/mini-css-extract-plugin/dist/loader.js??ref--6-oneOf-1-0!./node_modules/css-loader/dist/cjs.js??ref--6-oneOf-1-1!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/stylePostLoader.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--6-oneOf-1-2!./node_modules/postcss-loader/src??ref--6-oneOf-1-3!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!D:/Java/projectku/安隅智能社区/Front/pages/community/community.vue?vue&type=style&index=0&id=2c27ccda&scoped=true&lang=css& ***!
  \*********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// extracted by mini-css-extract-plugin
    if(false) { var cssReload; }
  

/***/ })

},[[64,"common/runtime","common/vendor"]]]);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/community/community.js.map