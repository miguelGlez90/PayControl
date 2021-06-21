
grails.plugin.springsecurity.secureChannel.useHeaderCheckChannelSecurity = true
grails.plugin.springsecurity.portMapper.httpPort = 80
grails.plugin.springsecurity.portMapper.httpsPort = 443
grails.plugin.springsecurity.secureChannel.secureHeaderName = 'X-Forwarded-Proto'
grails.plugin.springsecurity.secureChannel.secureHeaderValue = 'http'
grails.plugin.springsecurity.secureChannel.insecureHeaderName = 'X-Forwarded-Proto'
grails.plugin.springsecurity.secureChannel.insecureHeaderValue = 'https'
grails.plugin.springsecurity.successHandler.alwaysUseDefault = true
grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/'

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'prod.cuernasoft.com.seguridad.Usuario'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'prod.cuernasoft.com.seguridad.UsuarioRole'
grails.plugin.springsecurity.authority.className = 'prod.cuernasoft.com.seguridad.Role'
grails.plugin.springsecurity.requestMap.className = 'prod.cuernasoft.com.seguridad.Requestmap'
grails.plugin.springsecurity.securityConfigType = 'Requestmap'

// Added by the Spring Security Core plugin:: Encriptado de passwords
grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.password.algorithm = 'SHA-256'
grails.plugin.springsecurity.password.hash.iterations = 6
grails.plugin.springsecurity.useSessionFixationPrevention = false

