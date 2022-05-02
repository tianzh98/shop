import hyRequest from "@/http";

// 根据订单id查询订单详情
export const getOrderDetail = data => {
  return hyRequest.post({ url: "/order/getOrderDetail", data });
};

//data dictionary
export const updateReceiverInfo = data => {
  return hyRequest.post({ url: "/order/changeReceiveInfo", data });
};
//data dictionary
export const updateMoneyInfo = data => {
  return hyRequest.post({ url: "/system/enum/getEnum.pub", data });
};
//data dictionary
export const updateOrderNote = data => {
  return hyRequest.post({ url: "/system/enum/getEnum.pub", data });
};
/*
//data dictionary
export const closeOrder = data => {
  return hyRequest.post({ url: "/system/enum/getEnum.pub", data });
};
//data dictionary
export const deleteOrder = data => {
  return hyRequest.post({ url: "/system/enum/getEnum.pub", data });
};*/


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
//关闭订单
export const closeOrder = data => {
  return hyRequest.post({ url: "/order/closeOrder", data });
};
//删除订单
export const deleteOrder = data => {
  return hyRequest.post({ url: "/order/deleteOrder", data });
};
//订单发货
export const deliveryOrder = data => {
  return hyRequest.post({ url: "/order/deliveryOrder", data });
};
