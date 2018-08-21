#!/usr/bin/env tarantool
log = require('log')

-- Configure database
box.cfg {
    listen=3301,
    wal_mode="none",
    background = true,
    log = 'logs/cache_app.log',
    pid_file = 'logs/cache_app.pid',
    log_level = 7,
    memtx_memory=1073741824
}

function init_database()
    box.schema.user.grant('guest', 'execute,received,write,read', 'universe')
    box.schema.create_space('tokens', {id = 1000})
    box.space.tokens:format({
        {name = 'name', type = 'string'},
        {name = 'bpmloader', type = 'string'},
        {name = 'aspxauth', type = 'string'},
        {name = 'username', type = 'string'},
        {name = 'bpmsessionid', type = 'string'}
    })
    -- box.space.tokens:create_index('primary', {type='TREE', parts={'id'}})
    box.space.tokens:create_index('primary', {type='TREE', parts={'name'}})
    box.schema.user.create('tokens_user', { password = 'tokens_pwd' })
    box.schema.user.grant('tokens_user', 'read,write,execute', 'universe')
end

box.once('init', init_database)
