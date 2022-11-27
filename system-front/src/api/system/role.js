import request from '@/utils/request'


const api_name='/admin/system/sysRole/'
export default {

    getPageList(page,limit,searchObj) {
    return request({
        url: '/admin/system/sysRole/'+page+"/"+limit,
        method: 'get',
        params: searchObj
    })
    },

    removeId(id) {
        console.log('111111')
        return request({
            //接口路径
            url: `${api_name}/remove/${id}`,

            method: 'delete'
        })
        },

    saveRole(role){
        return request({
            //接口路径
            url: `${api_name}/save`,
            method: 'post',
            data: role
        })
    },

    getRoleId(id){
        return request({
            //接口路径
            url: `${api_name}/findSysRoleById/${id}`,
            method: 'post'
        })
    },

    update(role){
        return request({
            //接口路径
            url: `${api_name}/update`,
            method: 'post',
            data: role
        })
    },
    batchremoveId(listId) {
        return request({
            //接口路径
            url: `${api_name}/BatchRemove`,
            method: 'delete',
            data: listId
        })
        },

        
        getRolesByUserId(userId) {
            return request({
              url: `${api_name}/toAssign/${userId}`,
              method: 'get'
            })
          },
          
          //分配角色
          assignRoles(assginRoleVo) {
            return request({
              url: `${api_name}/doAssign`,
              method: 'post',
              data: assginRoleVo
            })
          }

}
