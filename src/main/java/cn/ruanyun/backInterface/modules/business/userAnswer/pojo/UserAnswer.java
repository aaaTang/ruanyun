package cn.ruanyun.backInterface.modules.business.userAnswer.pojo;

import cn.ruanyun.backInterface.base.RuanyunBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户问答
 * @author z
 */
@Data
@Entity
@Table(name = "t_user_answer")
@TableName("t_user_answer")
public class UserAnswer extends RuanyunBaseEntity {

    private static final long serialVersionUID = 1L;


    private String title;

}