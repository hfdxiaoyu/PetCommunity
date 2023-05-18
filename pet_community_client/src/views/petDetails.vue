<template>
  <div class="main">
    <div style="width: 15%"></div>

  <!--宠物详细信息  -->
  <div class="post-detail">
    <h2 class="post-title">{{ this.oneContent.title }}</h2>
    <p class="post-meta">
      Created at {{ creDateFormat(this.oneContent.createtime) }} by <a href="#">{{ this.oneContent.nickName }}</a>,
      View: {{ this.oneContent.viewCount }}
    </p>
    <div class="post-content">
      <div class="demo-image">
        <div class="block" v-for="fit in fits" :key="fit">
          <el-image
            style="width: 400px; height: 400px"
            :src="img"
            :fit="fit"></el-image>
        </div>
      </div>

      {{ this.oneContent.content }}
    </div>

    <div>
      <pet-message></pet-message>
    </div>
  </div>

  <div style="width: 15%"></div>
  </div>
</template>

<script>
import util1 from '../utils/util1.js'
import message from "./message";

export default {
  name: "petDetails",
  data() {
    return {
      contenid: this.$route.query.contenid, //接收跳转页面传过来的参数
      rePageNum: 1, //评论页数
      rePageSize: 5, //评论一页多少条
      oneContent: {}, //文章
      img:'',
      fits:['scale-down'],
    }
  },
  methods: {
    load() { //加载文章跟评论信息
      this.loadArticle()
    },
    loadArticle() { //加载文章
      this.request.get('/bbsContent/getOneContent/' + this.contenid
      ).then(
        res => {
          //获取数据并且拼接进数组
          this.oneContent = res.data.data
          this.img = res.data.data.img
        }
      )
    },
    loadReplay() {//加载评论信息
      this.request.get('/bbsReplay/page', {
        params: {
          pageNum: this.rePageNum,
          pageSize: this.rePageSize,
          articleId: this.contenid, //内容id
        }
      }).then(
        res => {
          //获取数据并且拼接进数组
          this.contentList = this.contentList.concat(res.data.data.records)
          this.total = res.data.data.total
        }
      )
    },
    creDateFormat(date){//对时间进行格式化处理
      var date1 = util1.dateFormatStr(date);
      return date1
    }

  },
  created() {
    this.load()
  },
  components:{ //定义组件
    'pet-message':message,
  }

}
</script>

<style scoped>
.main{
  display: flex;
  flex-direction: row;
}
.post-detail {
  text-align: center;
  width: 70%;
  display: flex;
  flex-direction: column;
}
.post-title {
  margin-bottom: .25rem;
  font-size: 2.5rem;
}
.post-meta {
  margin-bottom: 1.25rem;
  color: #999;
}
.post-content {
  font-size: 1.1rem;
  font-weight: 400;
  line-height: 1.5;
  color: #212529;
}

</style>
