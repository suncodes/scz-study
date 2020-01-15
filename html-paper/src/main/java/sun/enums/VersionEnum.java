package sun.enums;

import org.springframework.beans.factory.annotation.Value;

/**
 * 版本枚举
 */
public enum VersionEnum {
	//教材对应的版本及编码
	REN_JIAO(1, "人教"),
	BEI_SHI(2, "北师"),
	HUA_SHI(3,"华师"),
	LU_JIAO(4, "鲁教"),
	JI_JIAO(5, "冀教");

	/** 版本编码 */
	private Integer code;

	/** 版本描述 */
	private String desc;

	VersionEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 根据版本的文字描述获取对应的枚举
	 * @param desc
	 * @return
	 */
	public static VersionEnum getByDesc(String desc, VersionEnum defaultNull) {
		for (VersionEnum versionEnum: VersionEnum.values()) {
			if (versionEnum.getDesc().equals(desc)) {
				return versionEnum;
			}
		}
		return defaultNull;
	}

	/**
	 * 根据版本的编码获取对应的文字描述
	 * @param version 版本编码
	 * @return 版本描述
	 */
	public static String getDescByCode(String version) {
		for (VersionEnum versionEnum : VersionEnum.values()) {
			if (versionEnum.getCode() == Integer.parseInt(version)) {
				return versionEnum.getDesc();
			}
		}
		return null;
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
