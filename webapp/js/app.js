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

try {
    require('./init');
    var Form = require('./form');
    var ExtensionService = require('live/services/extension');

    const slack = {
        name: 'Slack',
        type: 'slack',
        origin: 'Plugin Slack',
        roles: ['notification'],
        icon: '/content/plugin-slack/slack.png',
        ui: {
            form: Form,
            view: null
        }
    };

    ExtensionService.register(slack);
}
catch (e) {
    if (process.env.NODE_ENV !== 'production') console.error(e);
}
