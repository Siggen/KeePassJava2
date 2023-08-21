/*
 * Copyright 2023 Giuseppe Valente
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.linguafranca.pwdb.kdbx.jackson.converter;

import java.util.Date;

import org.linguafranca.pwdb.kdbx.Helpers;

import com.fasterxml.jackson.databind.util.StdConverter;

public class StringToDateConverter extends StdConverter<String, Date> {

    @Override
    public Date convert(String value) {
        Date result = null;
        if(value != null) {
            if(value.equals("${creationDate}")) {
                result = new Date();
            }
            try {
                result =  Helpers.toDate(value);
            } catch(Exception e) {
                result = new Date();
            }
        }
        return result;
    }
}
