package io.github.pleuvoir.fastlog.config;

import io.github.pleuvoir.fastlog.utils.StringUtils;
import java.util.Map;


public class ReloadPropertiesableSupport {

	public static abstract class Adapter implements ReloadPropertiesableProvider {

		protected abstract Map<String, String> getConfig();

		@Override
		public String getRequiredProperty(String name) {
			String value = getString(name);
			if (StringUtils.isBlank(value)) {
				throw new RuntimeException("配置项: " + name + " 值为空");
			}
			return value;
		}

		@Override
		public String getString(String name) {
			return getConfig().get(name);
		}

		@Override
		public String getString(String name, String defaultValue) {
			return getConfig().getOrDefault(name, defaultValue);
		}

		@Override
		public int getInt(String name) {
			return Integer.parseInt(getRequiredProperty(name));
		}

		@Override
		public int getInt(String name, int defaultValue) {
			String value = getString(name);
			if (StringUtils.isBlank(value)) {
				return defaultValue;
			}
			return Integer.parseInt(value);
		}

		@Override
		public long getLong(String name) {
			return Long.parseLong(getRequiredProperty(name));
		}

		@Override
		public long getLong(String name, long defaultValue) {
			String value = getString(name);
			if (StringUtils.isBlank(value)) {
				return defaultValue;
			}
			return Long.parseLong(value);
		}

		@Override
		public double getDouble(String name) {
			return Double.parseDouble(getRequiredProperty(name));
		}

		@Override
		public double getDouble(String name, double defaultValue) {
			String value = getString(name);
			if (StringUtils.isBlank(value)) {
				return defaultValue;
			}
			return Double.parseDouble(value);
		}

		@Override
		public boolean getBoolean(String name, boolean defaultValue) {
			String value = getString(name);
			if (StringUtils.isBlank(value)) {
				return defaultValue;
			}
			return Boolean.parseBoolean(value);
		}

		@Override
		public boolean getBoolean(String name) {
			return Boolean.parseBoolean(getRequiredProperty(name));
		}

	}

	interface ReloadPropertiesableProvider {

		String getRequiredProperty(String name);

		String getString(String name);

		String getString(String name, String defaultValue);

		int getInt(String name);

		int getInt(String name, int defaultValue);

		long getLong(String name);

		long getLong(String name, long defaultValue);

		double getDouble(String name);

		double getDouble(String name, double defaultValue);

		boolean getBoolean(String name);

		boolean getBoolean(String name, boolean defaultValue);
	}

}
