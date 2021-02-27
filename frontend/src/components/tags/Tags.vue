<template>
  <div>
    <div>
      <ul>
        <li v-for="tag in tagList" :key="tag.id">
          {{ tag.title }}
        </li>
      </ul>
    </div>
    <div>
      <label>
        <input
          type="text"
          placeholder="tag 입력 창"
          v-model="tag"
          @keypress.enter="onKeyPress"
        />
      </label>
    </div>
  </div>
</template>

<script lang="ts">
  const tagList = [
    { id: 1, title: '개발' },
    { id: 2, title: '디자인' },
    { id: 3, title: '인문학' },
  ];

  import { Vue, Component } from 'vue-property-decorator';
  @Component
  export default class Tags extends Vue {
    private tagList = tagList;
    private tag = '';

    
  }
</script>

<style scoped></style>
