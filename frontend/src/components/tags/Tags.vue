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
    { id: 3, title: '테마여행' },
    { id: 4, title: '잡지' },
    { id: 5, title: '인문' },
    { id: 6, title: '사회' },
    { id: 7, title: '자연' },
    { id: 8, title: '사랑' },
    { id: 9, title: '사람' },
    { id: 10, title: '사탕' },
    { id: 11, title: '람사사사사' },
  ];

  import { Vue, Component, PropSync } from 'vue-property-decorator';
  @Component
  export default class Tags extends Vue {
    @PropSync('accountsTagList') private accountsTagListSync!: {
      id: number;
      title: string;
    }[];
    private tagList = tagList;
    private tag = '';

    private onKeyPress({ target }: { target: HTMLInputElement }): void {
      if (!this.isDuplicate(target.value)) {
        this.tagList.push({
          id: this.tagList.length + 1,
          title: target.value,
        });
      }
      this.tag = '';
    }

    private isDuplicate(title: string): boolean {
      return this.tagList.some(tag => tag.title === title);
    }
  }
</script>

<style scoped></style>
