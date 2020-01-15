package sun.enums;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 试题模拟试卷子类型枚举
 */
public enum ResourceSubTypeEnum {
	//试卷子类型及对应编码
	ZK_SX_MN(1, "中考数学模拟试卷"),
//	ZZ_ER_JIAN(2, "郑州二检"),
//	ZZ_YI_JIAN(3, "郑州一检"),
	SCHOOL_EXAM(5, "名校试卷"),
	AREA_EXAM(6, "地区考试"),
//	KXCE(7, "开学测试卷")
    ZXSJ(7, "众享试卷");

	/** 试题子类型编码 */
	private Integer code;

	/** 试题子类型说明 */
	private String desc;

	ResourceSubTypeEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 根据试题子类型描述信息获取试题子类型
	 * @param desc
	 * @param defaultNull
	 * @return
	 */
	public static ResourceSubTypeEnum getByDesc(String desc, ResourceSubTypeEnum defaultNull) {
		for (ResourceSubTypeEnum resourceSubTypeEnum : ResourceSubTypeEnum.values()) {
			if (resourceSubTypeEnum.getDesc().equals(desc)) {
				return resourceSubTypeEnum;
			}
		}
		return defaultNull;
	}

	/**
	 * 根据试题子类型描述信息获取试题子类型
	 * @param code
	 * @param defaultNull
	 * @return
	 */
	public static ResourceSubTypeEnum getByCode(Integer code, ResourceSubTypeEnum defaultNull) {
		for (ResourceSubTypeEnum resourceSubTypeEnum : ResourceSubTypeEnum.values()) {
			if (resourceSubTypeEnum.getCode().equals(code)) {
				return resourceSubTypeEnum;
			}
		}
		return defaultNull;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
