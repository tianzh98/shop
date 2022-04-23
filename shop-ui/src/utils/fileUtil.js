// 将后端返回的图片 base64转成 url
export const resolvePicFileResultContext = function(res) {
  let base64String = res.data.base64String;
  let fileType = res.data.fileType;
  let id = res.data.id;
  // 将base64解码为二进制
  let decodeToBinary = Buffer.from(base64String, "base64").toString("binary");
  let n = decodeToBinary.length;
  let u8Arr = new Uint8Array(n);
  while (n--) {
    u8Arr[n] = decodeToBinary.charCodeAt(n);
  }
  let blob = new Blob([u8Arr], { type: fileType });
  let url = window.URL.createObjectURL(blob);

  return { id: id, url: url };
};

export const resolvePicFileRes = function(res) {
  let base64String = res.base64String;
  let fileType = res.fileType;
  let id = res.id;
  // 将base64解码为二进制
  let decodeToBinary = Buffer.from(base64String, "base64").toString("binary");
  let n = decodeToBinary.length;
  let u8Arr = new Uint8Array(n);
  while (n--) {
    u8Arr[n] = decodeToBinary.charCodeAt(n);
  }
  let blob = new Blob([u8Arr], { type: fileType });
  let url = window.URL.createObjectURL(blob);

  return { id: id, url: url };
};
