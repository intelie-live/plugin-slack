import i18n  from 'live/services/i18n';
import pt_br from './i18n/pt_br';
import en_us from './i18n/en_us';

var locale = (Live.settings && Live.settings.locale) ? Live.settings.locale : 'pt_br';
var localeFiles = {
    pt_br: pt_br,
    en_us: en_us
};

i18n.add(localeFiles[locale]);
