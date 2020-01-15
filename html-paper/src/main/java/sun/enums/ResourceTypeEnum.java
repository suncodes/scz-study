package sun.enums;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 入库试题资源类型
 */
public enum ResourceTypeEnum {
	//不同类型试题对应的编码、名称及路径
	LECTURE(1, "（讲义）", "lecture"),
    TTL(2, "天天练", "ttl"),
    PRACTICE(3, "（习题）", "xiti"),
    SUI_CE(4, "（随堂测试）", "suitang"),
    BEI_KAO(5, "备考", "exam"),
    ZJCE(6, "章节测试", "exam"),
    MNSJ(7, "模拟试卷", "exam"),
    //961_TTL(8, "9.61天天练试题"),
	SZYL(9, "实战演练", "exam"),
    ASK_ANSWER(10, "问与答", "exam"),
	ZKZT(11, "中考真题", "exam"),
	KE_BEN_TI(12, "课本题", "keben"),
	HJZY(13, "寒假作业", "xiti"),
	ZXJZ(14,"众享讲座", "xiti"),
	ZXZT(15,"众享专题", "exam"),
	SJZY(16, "暑假作业", "xiti"),
	JF(17, "教辅", "exam"),
	ZKYW(80, "中考语文", "exam"),
	/*POEM(18, "语文诗词", "poem"),*/
	TSWQ(90, "典型学生错题", "exam"),
	CTDRFX(99, "学生错题导入分析", "exam");

	/** 题资源类型 */
    private Integer type;

    /** 题资源类型描述 */
    private String desc;

    /** 小文件存放路径 */
    private String path;

	ResourceTypeEnum(Integer type, String desc, String path) {
		this.type = type;
		this.desc = desc;
		this.path = path;
	}

	/**
	 * 根据资源类型的文字描述获取对应的枚举
	 * @param desc
	 * @return
	 */
	public static ResourceTypeEnum getByDesc(String desc,  ResourceTypeEnum defaultNull) {
		for (ResourceTypeEnum resourceTypeEnum: ResourceTypeEnum.values()) {
			if (resourceTypeEnum.getDesc().equals(desc)) {
				return resourceTypeEnum;
			}
		}
		return defaultNull;
	}

	/**
	 * 根据资源类型获取相应描述
	 * @param type
	 * @return
	 */
	public static ResourceTypeEnum getByType(Integer type,  ResourceTypeEnum defaultNull) {
		for (ResourceTypeEnum resourceTypeEnum: ResourceTypeEnum.values()) {
			if (resourceTypeEnum.getType().equals(type)) {
				return resourceTypeEnum;
			}
		}
		return defaultNull;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPath(){
		return path;
	}

	public void setPath(String path){
		this.path = path;
	}
}
