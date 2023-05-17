<template>
  <!--宠物详细信息  -->
  <div class="article" @click="">
    <h4>{{ this.oneContent.title }}</h4>
    <div class="article-contents">
      <img :src="this.oneContent.avatar" v-if="this.oneContent.avatar === '' ? false:true">
      <span>
                <p>{{ creDateFormat(this.oneContent.createtime)  }}</p>
                <span>
                    <h5>作者：{{ this.oneContent.nickName }}</h5>
                    <span><i class="el-icon-magic-stick"></i>{{ this.oneContent.createtime }}</span>
                    <span><i class="el-icon-view"></i>{{ this.oneContent.viewCount }}</span>
                </span>
            </span>
    </div>
  </div>
</template>

<script>
import util1 from '../utils/util1.js'

export default {
  name: "petDetailsColum",
  data() {
    return {
      contenid: this.$route.query.contenid, //接收跳转页面传过来的参数
      rePageNum: 1, //评论页数
      rePageSize: 5, //评论一页多少条
      oneContent: {}, //文章

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
  }

}
</script>

<style scoped>
.article {
  width: 860px;
  margin-bottom: 14px;
  padding-bottom: 10px;
  border-bottom: whitesmoke solid 2px;
}

.article h4 {
  font-weight: 500;
  margin-bottom: 5px;
}

.article h4:hover {
  cursor: pointer;
  color: skyblue;
}

.article-contents {
  display: flex;
  flex-direction: row;
}

.article-contents img {
  width: 140px;
  border: 2px;
  margin-right: 5px;
}

.article-contents span {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.article-contents span span {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  margin-top: 14px;
}

.article-contents span span h5 {
  font-weight: 500;
  color: #605d5d;
}

.article-contents span span span {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin: 0px 40px;
  font-size: 14px;
  color: #646262;
}

.article-contents span span span i {
  margin-right: 4px;
}

.article-contents p {
  font-size: 14px;
  color: #646262;
  height: 40px;
  /*width: 710px;*/
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-line-clamp: 2;
  word-break: break-all;
  display: -webkit-box;
  -webkit-box-orient: vertical;
}

.article-contents p:hover {
  cursor: pointer;
}

</style>
