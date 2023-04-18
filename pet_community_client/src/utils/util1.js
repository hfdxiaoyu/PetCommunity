//工具类


  /**
   * 对时间进行格式化转换
   * @param date 后端的格式化日期
   * @returns {Date}
   * @constructor
   */
  function dateFormat(date) {
    //对传进来的数据进行解析
    var split1 = date.split('T');
    //把年月日分离出来
    var split2 = split1[0].split('-')
    //把时间分离出来
    var split3 = split1[1].split(':')
    //初始化date对象
    var date1 = new Date(split2[0],split2[1],split2[2],split3[0],split3[1],split3[2].substr(0,2),split3[3]);

    // console.log("构建出来的日期：",date1)
    // console.log('年:',date1.getFullYear(),'月:',date1.getMonth(),'日：',date1.getDay())
    return date1
  }

  /**
   * 返回格式化的时间处理数据
   * @param date
   * @returns {string}
   */
  function dateFormatStr(date) {
    var date1 = this.dateFormat(date);
    return date1.getFullYear() + '-' + date1.getMonth() + '-' + date1.getDay() + ' ' + date1.getHours() + ':' + date1.getMinutes() + ':' + date1.getSeconds()
  }

export default {dateFormat,dateFormatStr}
