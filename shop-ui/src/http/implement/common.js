import hyRequest from "../index";
import localCache from "@/utils/cache";

// get the menu data list
export const getMenuList = data => {
  return hyRequest.post({ url: "/auth/queryUserMenu", data });
};
export const loginDo = data => {
  return hyRequest.post({
    url: "/auth/logIn",
    data,
    headers: { token: localCache.getCookie("token") }
  });
};
//注册用户
export const registerUser = data => {
  return hyRequest.post({ url: "/auth/registerUser", data });
};
//获取性别下拉
export const getGender = data => {
  return hyRequest.post({ url: "/auth/getGender", data });
};

/*logout */
export const logOut = data => {
  return hyRequest.post({ url: "/auth/logOut", data });
};
//data dictionary
export const getDataDic = data => {
  return hyRequest.post({ url: "/system/enum/getEnum.pub", data });
};
//perm
export const getPerms = data => {
  return hyRequest.post({
    url: "/auth/queryUserPermission",
    data
  });
};
export const getColumns = data => {
  return hyRequest.post({ url: "/tableConfig/getSortedFieldConfig", data });
};

// 上传文件
export const uploadFile = data => {
  return hyRequest.post({ url: "/file/upload", data });
};

// 上传文件
export const getFileById = data => {
  return hyRequest.post({ url: "/file/getFileById", data });
};

//
export const modifyPwd = data => {
  return hyRequest.post({ url: "/auth/modifyPwd", data });
};
