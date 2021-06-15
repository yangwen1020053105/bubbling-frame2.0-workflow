export default {
    state: {
        tagsList: []
    },
    mutations: {
        setTagList(state, list) {
            let tagsList = []
            console.log(tagsList)
            if (list) {
                tagsList = [...list]
            } else {
                // 若有存入localstorage中时可考虑
                // tagsList = getTagsListFromLocalstorage() || []
            }
        }
    },
    getters: {
        tagsList: state => state.tagsList
    }
}