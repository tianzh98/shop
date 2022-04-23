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
    oldPicIdList: {
      type: Array,
      default: () => []
    },
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
      oldPicIdListTemp: [],
      // {id,url}
      fileList: []
    };
  },
  created() {
    this.initFileList();
  },
  activated() {
    this.initFileList();
  },
  watch: {
    fileList(newValue) {
      let newPicIdList = [];
      if (newValue) {
        for (let i = 0; i < newValue.length; i++) {
          newPicIdList.push(newValue[i].id);
        }
      }
      this.$emit("changeNewPicIdList", newPicIdList);
    },
    // 必须监听这个父组件值的变化，否则刷新页面后 子组件的值会效时
    oldPicIdList(val) {
      this.oldPicIdListTemp = [];
      this.oldPicIdListTemp.push(...val);
      this.initFileList();
    }
  },
  methods: {
    initFileList() {
      if (this.oldPicIdListTemp) {
        this.fileList = [];
        this.oldPicIdListTemp.forEach(id => {
          // 添加
          getFileById({ id: id }).then(res => {
            this.fileList.push(this.resolveRes(res));
          });
        });
      }
    },
    handleRemove(file) {
      if (this.fileList) {
        for (let i = 0; i < this.fileList.length; i++) {
          if (file.id === this.fileList[i].id) {
            this.fileList.splice(i, 1);
            break;
          }
        }
      }
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

      return { id: id, url: url };
    }
  }
};
</script>
<style></style>
