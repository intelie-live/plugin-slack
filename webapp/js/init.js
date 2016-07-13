/*
 * (C) Copyright 2016 Intelie (http://intelie.com/) and others.
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

import i18n  from 'live/services/i18n';
import pt_br from './i18n/pt_br';
import en_us from './i18n/en_us';

var locale = (Live.settings && Live.settings.locale) ? Live.settings.locale : 'pt_br';
var localeFiles = {
    pt_br: pt_br,
    en_us: en_us
};

i18n.add(localeFiles[locale]);
