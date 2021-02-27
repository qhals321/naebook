<template>
  <div>
    <div>
      <ul>
        <li v-for="tag in accountsTagListSync" :key="tag.id">
          {{ tag.title }}
        </li>
      </ul>
    </div>
    <div>
      <label>
        <input
          ref="inputRef"
          type="text"
          placeholder="tag 입력 창"
          v-model="tag"
          @input="onInput"
          @keypress.enter="onKeypressEnter"
          @keydown="onKeyDown"
        />
      </label>
      <ul v-if="matchedTagList.length > 0">
        <li v-for="tag in matchedTagList" :key="tag.id">
          {{ tag.title }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts">
  import { Tag } from '@/types/library';

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
  import { HTMLTargetType } from '@/types/common';
  @Component
  export default class Tags extends Vue {
    @PropSync('accountsTagList') private accountsTagListSync!: Tag[];
    private tagList = tagList;
    private matchedTagList: Tag[] = [];
    private tag = '';

    private onInput({ target }: HTMLTargetType): void {
      if (target.value === '') {
        this.resetInsertedTags();
        return;
      }

      this.setMatchedTagList(target.value);
    }

    private setMatchedTagList(title: string): void {
      this.matchedTagList = this.getFilteredTags(title);
    }

    private getFilteredTags(title: string): Tag[] {
      const filtered = this.tagList.filter(tag => tag.title.includes(title));

      return filtered.length > 0 ? filtered : [];
    }

    private focusOnCurrIdx: number | null = null;
    private onKeyDown({ keyCode }: { keyCode: number }): void {
      switch (keyCode) {
        case 38:
          if (this.focusOnCurrIdx === null) {
            this.focusOnCurrIdx = 0;
          } else if (this.focusOnCurrIdx > 0) {
            this.focusOnCurrIdx--;
          }
          this.tag = this.matchedTagList[this.focusOnCurrIdx].title;
          break;
        case 40:
          if (this.focusOnCurrIdx === null) {
            this.focusOnCurrIdx = 0;
          } else if (this.focusOnCurrIdx < this.matchedTagList.length - 1) {
            this.focusOnCurrIdx++;
          }
          this.tag = this.matchedTagList[this.focusOnCurrIdx].title;
          break;
        case 13:
          console.log(this.tag);
      }
    }

    private onKeypressEnter({ target }: HTMLTargetType): void {
      this.setAccountsTagList(target.value);
    }

    private setAccountsTagList(title: string): void {
      if (!this.isDuplicate(title)) {
        this.accountsTagListSync.puth({
          id: this.accountsTagListSync.length + 1,
          title,
        });
      }
      this.resetInsertedTags();
    }

    private isDuplicate(title: string): boolean {
      return this.tagList.some(tag => tag.title === title);
    }

    private resetInsertedTags(): void {
      this.tag = '';
      this.matchedTagList = [];
    }
  }
</script>

<style scoped></style>
