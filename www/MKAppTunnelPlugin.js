var exec = require('cordova/exec');

exports.getIdByHttpUrlConnection = function(success, error,arg0) {
    exec(success, error, "MKAppTunnelPlugin", "getIdByHttpUrlConnection", [arg0]);
};
