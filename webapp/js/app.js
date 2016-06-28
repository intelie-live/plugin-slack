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
