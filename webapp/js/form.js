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

'use strict';

var React = require('react');

var i18n = require('live/services/i18n');

var ValidationMixin = require('live/components/forms/mixins/validation-mixin'),
    ObjectInitializerMixin = require('live/components/forms/mixins/object-initializer')('/rest/extension', {
        type: 'slack',
        active: true,
        config: {
            webhook: 'https://your.slack.webhook.com',
            defaultChannel: '#intelie',
            username: 'live',
            enableForNotification: true
        },
        fullQualifier: null,
        qualifier: null,
        status: {}
    });

var FormBase = require('live/components/extensions/ui/form');

var FormGroup = require('live/components/forms/ui/form-group');

module.exports = React.createClass({
    mixins: [ObjectInitializerMixin, ValidationMixin],
    validations: {
        properties: {
            config: {
                properties: {
                    webhook: {
                        required: true,
                        allowEmpty: false,
                        message: i18n('webhook must not be empty')
                    },
                    username: {
                        required: true,
                        allowEmpty: false,
                        message: i18n('username must not be empty')
                    },
                    defaultChannel: {
                        required: true,
                        allowEmpty: false,
                        message: i18n('defaultChannel must not be empty')
                    }
                }
            }
        }
    },
    getInitialState: function () {
        return {};
    },
    onChangeCheckbox: function (e) {
        var object = this.state.object,
            config = object.config,
            field = e.target.name;

        config[field] = !config[field];
        this.applyObj({config: config});
    },
    _onSuccess: function () {
        window.location.href = '#/integrations/' + this.state.object.type + '/' + this.state.object.id;
    },
    _onDelete: function () {
        window.location.href = '#/integrations/';
    },
    render: function () {
        var object = this.state.object,
            config = object.config;

        return (
            <FormBase self={this}>
                <div className="form-area">
                    <h3>{i18n('Connection config')}</h3>

                    <FormGroup type="text" label={
                                <span>{i18n('Webhook')}
                                <abbr title="required">*</abbr></span>
                            }
                           name="config.webhook" value={config.webhook}
                           onChange={this.handleValidation}
                           validationState={this.validationState('config.webhook')}
                           hasFeedback help={this.errorsFor('config.webhook')}/>

                    <FormGroup type="text" label={<span>{i18n('Default Channel')}<abbr title="required">*</abbr></span>}
                           name="config.defaultChannel" value={config.defaultChannel}
                           onChange={this.handleValidation}
                           validationState={this.validationState('config.defaultChannel')}
                           hasFeedback help={this.errorsFor('config.defaultChannel')}/>

                    <FormGroup type="text" label={<span>{i18n('Username')}<abbr title="required">*</abbr></span>}
                           name="config.username" value={config.username}
                           onChange={this.handleValidation}
                           validationState={this.validationState('config.username')}
                           hasFeedback help={this.errorsFor('config.username')}/>

                </div>
            </FormBase>
        );
    }
});
