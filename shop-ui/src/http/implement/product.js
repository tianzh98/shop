import hyRequest from "../index";

// 获取商品列表
export const getProductList = data => {
  return hyRequest.post({ url: "/product/queryProductList", data });
};
