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
        <li
          v-for="(tag, idx) in matchedTagList"
          :key="tag.id"
          :class="{ focus: idx === focusOnCurrIdx }"
          @keypress.enter="onMatchedTagPress(tag.title)"
          @mouseover="focusOnCurrIdx = idx"
          @click="onMatchedTagPress(tag.title)"
        >
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

  const KEY_CODE = {
    UP: 38,
    DOWN: 40,
    ENTER: 13,
  } as const;

  import { Vue, Component, PropSync, Ref } from 'vue-property-decorator';
  import { HTMLTargetType } from '@/types/common';
  @Component
  export default class Tags extends Vue {
    @PropSync('accountsTagList')
    private accountsTagListSync!: Tag[];

    @Ref()
    private readonly inputRef!: HTMLInputElement;

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
        case KEY_CODE.UP:
          this.moveUp();
          break;
        case KEY_CODE.DOWN:
          this.moveDown();
          break;
        case KEY_CODE.ENTER:
          this.resetCurrentFocusStyle();
      }
    }

    private moveUp(): void {
      if (this.focusOnCurrIdx === null) {
        this.focusOnCurrIdx = 0;
        return;
      }
      if (this.focusOnCurrIdx > 0) {
        this.focusOnCurrIdx--;
        this.tag = this.matchedTagList[this.focusOnCurrIdx].title;
      }
    }

    private moveDown(): void {
      if (this.focusOnCurrIdx === null) {
        this.focusOnCurrIdx = 0;
        return;
      }
      if (this.focusOnCurrIdx < this.matchedTagList.length - 1) {
        this.focusOnCurrIdx++;
        this.tag = this.matchedTagList[this.focusOnCurrIdx].title;
      }
    }

    private onKeypressEnter({ target }: { target: HTMLInputElement }): void {
      this.setAccountsTagList(target.value);
    }

    private onMatchedTagPress(title: string): void {
      this.setAccountsTagList(title);
      this.resetCurrentFocusStyle();
      this.inputRef.focus();
    }

    private setAccountsTagList(title: string): void {
      if (!this.isDuplicate(title)) {
        this.accountsTagListSync.push({
          id: this.accountsTagListSync.length + 1,
          title,
        });
      }
      this.resetInsertedTags();
    }

    private isDuplicate(title: string): boolean {
      return this.accountsTagListSync.some(tag => tag.title === title);
    }

    private resetInsertedTags(): void {
      this.tag = '';
      this.matchedTagList = [];
    }

    private resetCurrentFocusStyle(): void {
      this.focusOnCurrIdx = null;
    }
  }
</script>

<style scoped>
  .focus {
    background-color: red;
  }
</style>
