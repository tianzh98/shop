import hyRequest from "../index";

// 获取商品列表
export const getProductList = data => {
  return hyRequest.post({ url: "/product/getProductList", data });
};

// 保存 或者 编辑商品信息
export const saveOrUpdateProductDetail = data => {
  return hyRequest.post({ url: "/product/saveOrUpdateProductDetail", data });
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
// 插入商品品牌
export const insertBrand = data => {
  return hyRequest.post({ url: "/brand/insertBrand", data });
};
// 删除商品品牌
export const deleteBrandById = data => {
  return hyRequest.post({ url: "/brand/deleteBrandById", data });
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





//获取商品类型列表下拉
export const getProductAttributeCategoryDropDown = data => {
  return hyRequest.post({
    url: "/product/getProductAttributeCategoryDropDown",
    data
  });
};

//获取商品类型列表
export const getProductAttributeCategoryList = data => {
  return hyRequest.post({ url: "/product/getProductAttributeList", data });
};

//获取商品类型列表
export const getProductAttributeParam = data => {
  return hyRequest.post({ url: "/product/getProductAttributeParam", data });
};




// 商品编辑页，下拉商品属性时候，获取商品规格、属性列表
export const getProductAttribute = data => {
  return hyRequest.post({ url: "/product/getProductAttribute", data });
};
// 获取商品库存信息
export const getSkuStockList = data => {
  return hyRequest.post({ url: "/product/getSkuStockList", data });
};

// 获取商品的属性信息
export const getProductAttributeValueList = data => {
  return hyRequest.post({ url: "/product/getProductAttributeValueList", data });
};



//获取订单列表
export const getOrderList = data => {
  return hyRequest.post({ url: "/order/getOrderList", data });
};
//获取订单支付类型下拉
export const getPayType = data => {
  return hyRequest.post({ url: "/order/getPayType", data });
};
//获取订单状态下拉
export const getStatus = data => {
  return hyRequest.post({ url: "/order/getStatus", data });
};




//获取所有地址
export const getAddressList = data => {
  return hyRequest.post({ url: "/address/getAddressList", data });
};
