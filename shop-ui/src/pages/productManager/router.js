// let r = require.context("./", true, /\.vue/);
// // route path的前缀. 请填写为：与本js文件所在文件夹的名称相同
// let prefix = 'product';
//
// let routers = r.keys().map(dir => {
//   // let name = path.split("/")[1].split(".")[0];
//   // 去掉.vue后缀  去掉开头的'.'
//   let pathSuffix = dir.substring(0, dir.length - 4).substr(1);
//   let path = `/${prefix}${pathSuffix}`;
//   let route = {
//     path: path,
//     name: path,
//     component: () => import(`${dir}`)
//   };
//   return route;
// });
//

// let routers = [
//   {
//     path: "/product",
//     redirect: "/product/productList",
//
//     name: "ProductList",
//     meta: { title: "商品" },
//     children: [
//       {
//         path: "productList",
//         name: "productList",
//         component: () => import("./product/ProductList"),
//         meta: { title: "商品列表" }
//       },
//       {
//         path: "addProduct",
//         name: "addProduct",
//         component: () => import("./product/ProductDetail"),
//         meta: { title: "添加商品" }
//       }
//     ]
//   }
// ];
// export default routers;
