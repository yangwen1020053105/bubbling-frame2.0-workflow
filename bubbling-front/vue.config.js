const path = require('path');//引入path模块
function resolve(dir){
    return path.join(__dirname,dir)//path.join(__dirname)设置绝对路径
}
// vue.config.js for less-loader@6.0.0
module.exports = {
    css: {
        loaderOptions: {
            less: {
                lessOptions: {
                    // If you are using less-loader@5 please spread the lessOptions to options directly
                    modifyVars: {
                        'primary-color': '#1DA57A',
                        'link-color': '#1DA57A',
                    },
                    javascriptEnabled: true,
                },
            },
        },
    },
    chainWebpack:(config)=>{
        config.resolve.alias
            //set第一个参数：设置的别名，第二个参数：设置的路径
            .set('@',resolve('./src'))
    },
};