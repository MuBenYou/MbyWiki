<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <div class="about">
        <h1>带你指数</h1>
      </div>

    </a-layout-content>
  </a-layout>

</template>

<script lang="ts">
import { defineComponent } from 'vue';
import TheHeader from '@/components/the-header.vue';
import TheFooter from '@/components/the-footer.vue';
export default defineComponent({
  name: 'damin-ebook'
});
</script>