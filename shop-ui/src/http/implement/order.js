import hyRequest from "@/http";

// 根据订单id查询订单详情
export const getOrderDetail = data => {
  return hyRequest.post({ url: "/order/getOrderDetail", data });
};
//data dictionary
export const updateReceiverInfo = data => {
  return hyRequest.post({ url: "/system/enum/getEnum.pub", data });
};
//data dictionary
export const updateMoneyInfo = data => {
  return hyRequest.post({ url: "/system/enum/getEnum.pub", data });
};
//data dictionary
export const closeOrder = data => {
  return hyRequest.post({ url: "/system/enum/getEnum.pub", data });
};
//data dictionary
export const updateOrderNote = data => {
  return hyRequest.post({ url: "/system/enum/getEnum.pub", data });
};
//data dictionary
export const deleteOrder = data => {
  return hyRequest.post({ url: "/system/enum/getEnum.pub", data });
};
