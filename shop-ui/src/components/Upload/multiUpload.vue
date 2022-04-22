<template>
  <div>
    <el-upload
      action="#"
      :http-request="handleUpload"
      :data="null"
      list-type="picture-card"
      :file-list="fileList"
      :before-upload="beforeUpload"
      :on-remove="handleRemove"
      :on-success="handleUploadSuccess"
      :on-preview="handlePreview"
      :limit="maxCount"
      :on-exceed="handleExceed"
    >
      <i class="el-icon-plus"></i>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="" />
    </el-dialog>
  </div>
</template>
<script>
import { getFileById, uploadFile } from "@/http/implement/common";

export default {
  name: "multiUpload",
  props: {
    //图片属性数组
    picIdList: Array,
    //最大上传图片数量
    maxCount: {
      type: Number,
      default: 5
    }
  },
  data() {
    return {
      dialogVisible: false,
      dialogImageUrl: null,
      // {id,url}
      fileList: []
    };
  },
  created() {
    this.getFileList();
  },
  computed: {
    // fileList() {
    //   let fileList = [];
    //   for (let i = 0; i < this.picIdList.length; i++) {
    //     fileList.push({url: this.picIdList[i]});
    //   }
    //   return fileList;
    // }
  },
  methods: {
    getFileList() {
      if (this.picIdList) {
        this.picIdList.forEach(id => {
          for (let i = 0; i < this.fileList.length; i++) {
            let fileIdAndUrl = this.fileList.get(i);
            if (
              fileIdAndUrl.id &&
              fileIdAndUrl.id === id &&
              !fileIdAndUrl.url
            ) {
              getFileById({ id: id }).then(res => {
                fileIdAndUrl.url = this.resolveRes(res).url;
              });
              break;
            }
          }
          // 添加
          getFileById({ id: id }).then(res => {
            this.fileList.push(this.resolveRes(res));
          });
        });
      }
    },
    handleRemove(file, fileList) {
      this.emitInput(fileList);
    },
    handlePreview(file) {
      this.dialogVisible = true;
      this.dialogImageUrl = file.url;
    },
    beforeUpload() {
      // console.log(file);
    },
    handleUpload(param) {
      // 创建form对象,必须使用这个,会自动把content-type设置成multi-part-form
      let formData = new FormData();
      formData.append("file", param.file);
      uploadFile(formData).then(res => {
        // 返回 图片在数据库中的id
        param.onSuccess(res);
      });
    },
    handleUploadSuccess(res) {
      // url = file.url;
      if (!this.fileList) {
        this.fileList = [];
      }
      this.fileList.push(this.resolveRes(res));
    },
    handleExceed() {
      this.$message({
        message: "最多只能上传" + this.maxCount + "张图片",
        type: "warning",
        duration: 1000
      });
    },

    resolveRes(res) {
      let base64String = res.data.base64String;
      let fileType = res.data.fileType;
      let id = res.data.id;
      // 将base64解码为二进制
      let decodeToBinary = Buffer.from(base64String, "base64").toString(
        "binary"
      );
      let n = decodeToBinary.length;
      let u8Arr = new Uint8Array(n);
      while (n--) {
        u8Arr[n] = decodeToBinary.charCodeAt(n);
      }
      let blob = new Blob([u8Arr], { type: fileType });
      let url = window.URL.createObjectURL(blob);
      console.log(this.fileList);

      return { id: id, url: url };
    }
  }
};
</script>
<style></style>
