/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

/**
 * @author Oliver Drotbohm
 */
@Converter(autoApply = true)
public class UuidAttributeConverter implements AttributeConverter<SomeValue, UUID> {

	@Override
	public UUID convertToDatabaseColumn(SomeValue attribute) {
		return attribute.getValue();
	}

	@Override
	public SomeValue convertToEntityAttribute(UUID dbData) {
		return new SomeValue(dbData);
	}
}
