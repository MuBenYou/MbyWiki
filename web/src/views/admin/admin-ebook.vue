<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '80px'  }"
    >
      <p>
        <a-button type="primary" @click="add()" size="large">
          新增
        </a-button>
      </p>
      <!--列,key id,数据ebook,分页,等待框,分页执行方法-->
      <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="ebooks"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
      >
        <template #cover="{text:cover}"><!--图片-->
          <img class="img-wh" v-if="cover" :src="cover" alt="avatar"/>
        </template>
        <template v-slot:action="{ text, record }">
          <a-space size="small">
            <a-button type="primary" @click="edit(record)">
              编辑
            </a-button>
            <a-button type="primary">
              删除
            </a-button>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>
  <a-modal
      title="Title"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >
    <!--弹出表单-->
    <a-form :model="ebook" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="封面">
        <a-input v-model:value="ebook.cover"/>
      </a-form-item>
      <a-form-item label="名称">
        <a-input v-model:value="ebook.name"/>
      </a-form-item>
      <a-form-item label="分类一">
        <a-input v-model:value="ebook.category1_id"/>
      </a-form-item>
      <a-form-item label="分类二">
        <a-input v-model:value="ebook.category2_id"/>
      </a-form-item>
      <a-form-item label="描述">
        <a-input v-model:value="ebook.description" type="textarea"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';//写上onMounted VUE3.0 setup集成了 导入ref 做响应式数据
import axios from 'axios';

export default defineComponent({
  name: 'AdminEbook',
  setup() {
    const param = ref();
    param.value = {};
    const ebooks = ref();//响应式数据 获取的书籍实时反馈到页面上
    const pagination = ref({
      current: 1,//当前页
      pageSize: 2,//分页条数
      total: 0
    });

    const loading = ref(false);

    const columns = [//页面的响应变量 不是数据的响应变量 代表就是这个表格里面有多少个数据 下面数据我们自己定义的
      {
        title: '封面',
        dataIndex: 'cover',
        slots: {customRender: 'cover'}//渲染
      },
      {
        title: '名称',
        dataIndex: 'name',
      },
      {
        title: '分类',
        slots: {customRender: 'category'}
      },
      {
        title: '阅读数',
        dataIndex: 'viewCount',
      },
      {
        title: '点赞数',
        dataIndex: 'voteCount'
      },
      {
        title: 'Action',
        key: 'action',
        slots: {customRender: 'action'}
      }
    ];
    /**
     * 数据查询
     **/
    const handleQuery = (params: any) => {
      loading.value = true;
      axios.get("/ebook/list",{
        params:{//params自带的
          page:params.page,//params自己定义的
          size:params.size
        }
      }).then((response) => {//.then(自己定义)指接口成功返回的数据,包含请求头,请求体,等信息;
        loading.value = false;
        const data = response.data;
        console.log(data);
        ebooks.value = data.content.list;

        //重置分页按钮
        pagination.value.current = params.page;//点第二页的按钮的时候前端 不会刷新 还是第一页的地方 实际我们以及到第二页了
        pagination.value.total = data.content.total;
      });
    };
    /**
     * 表格点击页码时触发
     */
    const handleTableChange = (pagination: any) => {
      console.log("看看自带的分页参数都有啥：" + pagination);
      handleQuery({
        page: pagination.current,
        size: pagination.pageSize
      });
    };
    //表单
    const ebook = ref({});
    const modalVisible = ref(false);
    const modalLoading = ref(false);
    const handleModalOk = () =>{
      modalLoading.value = true;
    /*  setTimeout(() => {
        modalVisible.value = false;
        modalLoading.value = false;
      },2000);//2000毫秒*/
      axios.post("/ebook/save",ebook.value).then((response) =>{
        const data =response.data;
        if (data.success){
          modalVisible.value=false;
          modalLoading.value=false;
          //重新加载列表
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize,
          });
        }
        });
    };

    //编辑
    const edit = (record:any) => {
      modalVisible.value = true;
      ebook.value = record

    }
    //新增
    const add = () => {
      modalVisible.value = true;
      ebook.value = {};

    }

    onMounted(() => {
      handleQuery({
        page:1,
        size: pagination.value.pageSize,
      });

    });

    return {
      ebooks,//表格
      pagination,
      columns,
      loading,
      handleTableChange,

      edit,
      add,

      ebook,
      modalVisible,
      modalLoading,
      handleModalOk,

    }
  }
});
</script>

<!-- #scoped表示当前组件才有用 -->
<style scoped>
.img-wh {
  width: 50px;
  height: 50px;
  line-height: 50px;
  border-radius: 8%;
  margin: 5px 0;
}
</style>
