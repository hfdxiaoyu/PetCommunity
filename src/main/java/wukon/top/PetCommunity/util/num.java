package wukon.top.PetCommunity.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Auther:1306933
 * @Date:2023/8/21
 * @Description:wukon.top.PetCommunity.util
 * @version:1.0
 */
public class num {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入一个字符串:");

        String str = sc.next();
        String str1 = "";
        for (int i = 0; i < str.length(); i++) {
            char ch1 = str.charAt(i);
            //判断是不是数字
            if (i > 0){
                if (Character.isDigit(ch1)){
                    for (int j = 0; j < Character.getNumericValue(ch1); j++) {
                        str1 += str.charAt(i-1);
                    }
                } else {
                    str1 += ch1;
                }
            }

        }

        System.out.println(str1);
    }
}
