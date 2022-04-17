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
  return hyRequest.post({ url: "/brand/getBrands", data });
};
// 获取商品品牌所有信息
export const getBrandDetail = data => {
  return hyRequest.post({ url: "/brand/getBrandDetail", data });
};
//获取商品联级目录下拉
export const getProductCategory = data => {
  return hyRequest.post({ url: "/product/getProductCategories", data });
};

// 查询商品分类详情
export const getProductCateDetail = data => {
  return hyRequest.post({ url: "/product/getProductCateDetail", data });
};

// 新增/修改商品分类
export const editProductCateDetail = data => {
  return hyRequest.post({ url: "/product/editProductCateDetail", data });
};
// 获取上级分类下拉框
export const getParentDropdown = data => {
  return hyRequest.post({ url: "/product/getParentDropdown", data });
};

// 新增/修改商品分类
export const deleteProductCateById = data => {
  return hyRequest.post({ url: "/product/deleteProductCateById", data });
};
