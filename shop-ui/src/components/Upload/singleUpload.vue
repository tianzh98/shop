<template>
  <div>
    <el-upload
      ref="upload"
      :action="useOss ? ossUploadUrl : minioUploadUrl"
      :http-request="handleUpload"
      :data="useOss ? dataObj : null"
      list-type="picture"
      :multiple="false"
      :show-file-list="showFileList"
      :file-list="fileList"
      :before-upload="beforeUpload"
      :on-remove="handleRemove"
      :on-success="handleUploadSuccess"
      :on-preview="handlePreview"
    >
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">
        只能上传jpg/png文件，且不超过10MB
      </div>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="fileList[0].url" alt="" />
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: "singleUpload",
  props: {
    value: String
  },
  computed: {
    imageUrl() {
      return this.value;
    },
    imageName() {
      if (this.value !== null && this.value !== "") {
        return this.value.substr(this.value.lastIndexOf("/") + 1);
      } else {
        return null;
      }
    },
    fileList() {
      return [
        {
          name: this.imageName,
          url: this.imageUrl
        }
      ];
    },
    showFileList: {
      get: function() {
        return (
          this.value !== null && this.value !== "" && this.value !== undefined
        );
      },
      set: function(newValue) {
        console.log(newValue);
      }
    }
  },
  data() {
    return {
      dataObj: {
        policy: "",
        signature: "",
        key: "",
        ossaccessKeyId: "",
        dir: "",
        host: ""
        // callback:'',
      },
      dialogVisible: false,
      useOss: false, //使用oss->true;使用MinIO->false
      ossUploadUrl: "http://macro-oss.oss-cn-shenzhen.aliyuncs.com",
      minioUploadUrl: "http://localhost:8081/file/upload"
    };
  },
  methods: {
    emitInput(val) {
      this.$emit("input", val);
    },
    handleRemove(file, fileList) {
      this.emitInput("");
      console.log(file + fileList);
    },
    handlePreview(file) {
      this.dialogVisible = true;
      console.log(file);
    },
    beforeUpload(file) {
      if (!this.useOss) {
        //不使用oss不需要获取策略
        return true;
      }
      console.log(file);
    },
    handleUpload(param){
      let file = param.file;
      let res = {
        data: {
          url: 'aaa'
        }
      };
      param.onSuccess(res);
      console.log(file);
    },
    handleUploadSuccess(res, file) {
      this.showFileList = true;
      this.fileList.pop();
      let url = this.dataObj.host + "/" + this.dataObj.dir + "/" + file.name;
      if (!this.useOss) {
        //不使用oss直接获取图片路径
        url = file.url;
      }
      this.fileList.push({ name: file.name, url: url });
      this.emitInput(this.fileList[0].url);
    }
  }
};
</script>
<style></style>
