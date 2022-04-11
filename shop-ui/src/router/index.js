import Vue from "vue";
import VueRouter from "vue-router";
import index from "@/pages/Index";
import localCache from "@/utils/cache";

Vue.use(VueRouter);
const originalPush = VueRouter.prototype.push;
//重写push方法，统一处理错误捕获
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err);
};
//配置默认页面,将默认页面的文件名赋值给defaultPage
let defaultInfo = {
  defaultPage: "Home",
  defaultTitle: "首页"
};
let home = {
  path: "/home",
  alias: "",
  name: "Home",
  meta: { title: "首页" },
  component: () => import(/* webpackChunkName: "home" */ "@/pages/Home.vue")
};

const routes = [
  {
    path: "/",
    name: "index",
    component: index,
    children: [home]
  },
  {
    path: "/Login",
    name: "Login",
    component: () => import(/* webpackChunkName: "login" */ "@/pages/Login.vue")
  },
  {
    path: "*",
    name: "error",
    component: () => import("@/pages/404")
  },
  {
    path: "/product",
    component: index,
    redirect: "/product/productList",
    name: "product",
    meta: { title: "商品" },
    children: [
      {
        path: "productList",
        name: "productList",
        component: () => import("@/pages/productManager/product/Product"),
        meta: { title: "商品列表" }
      },
      {
        path: "productDetail",
        name: "productDetail",
        component: () => import("@/pages/productManager/product/ProductDetail"),
        meta: { title: "添加商品" }
      },
      {
        path: "productCate",
        name: "productCate",
        component: () =>
          import("@/pages/productManager/productCate/ProductCate"),
        meta: { title: "商品分类", icon: "product-cate" }
      },
      {
        path: "productCateDetail",
        name: "productCateDetail",
        component: () =>
          import("@/pages/productManager/productCate/ProductCateDetail"),
        meta: { title: "添加商品分类" },
        hidden: true
      }
    ]
  }
];
// const files = require.context("@/pages", true, /router\.js$/);
// let child = files.keys().map(key => {
//   const page = require("@/pages" + key.replace(".", ""));
//   return page.default;
// });
// child = child.reduce((all, item) => {
//   for (let key in item) {
//     all.push(item[key]);
//   }
//   return all;
// }, []);

// let others = child.filter(item => {
//   if (item.name === defaultInfo.defaultPage) {
//     item.alias = "";
//     home = item;
//   }
//   return item.name !== defaultInfo.defaultPage;
// });
localCache.setSession("home", {
  url: home.path,
  title: defaultInfo.defaultTitle,
  name: home.name
});
// routes[0].children = routes[0].children.concat([home]).concat(others);

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});
router.beforeEach((to, from, next) => {
  // 登录界面登录成功之后，会把用户信息保存在会话
  // 存在时间为会话生命周期，页面关闭即失效。
  // let token = localCache.getCookie("token");
  // let userId = localCache.getCache("userInfo")?.id;
  if (to.path === "/Login") {
    if (localCache.isLogin()) {
      next({ path: "/" });
    } else {
      next();
    }
  } else {
    next();
    // if (!localCache.isLogin()) {
    //   next({ path: "/Login" });
    // } else {
    //   next();
    // }
  }
});
export default router;
