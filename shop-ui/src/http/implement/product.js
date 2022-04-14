import hyRequest from "../index";

// 获取商品列表
export const getProductList = data => {
  return hyRequest.post({ url: "/product/getProductList", data });
};

// 获取商品分类列表
export const getProductCateList = data => {
  return hyRequest.post({ url: "/product/getProductCateList", data });
};
// 获取商品品牌下拉
export const getBrands = data => {
  return hyRequest.post({ url: "/product/getBrands", data });
};
//获取商品联级目录下拉
export const getProductCategory = data => {
  return hyRequest.post({ url: "/product/getProductCategories", data });
};

