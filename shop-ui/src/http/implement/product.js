import hyRequest from "../index";

// 获取商品列表
export const getProductList = data => {
  return hyRequest.post({ url: "/product/getProductList", data });
};

// 获取商品分类列表
export const getProductCateList = data => {
  return hyRequest.post({ url: "/product/getProductCateList", data });
};
