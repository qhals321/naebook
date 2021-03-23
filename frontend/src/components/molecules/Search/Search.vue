<template>
  <div class="search-content">
    <!--	TODO: 이 부분을 atoms -> Filter 라는 이름으로 분리하는게 좋을지 고민중이에요...!! -->
    <search-label for="search-filter" class="search-content-filter">
      <search-select id="search-filter">
        <search-option
          v-for="(option, idx) in options"
          :key="idx"
          :value="option.value"
          :text="option.text"
        />
      </search-select>
    </search-label>
    <div class="search-content-keyword">
      <search-label for="keyword-input" class="keyword-input">
        <search-input id="keyword-input" />
      </search-label>
      <search-button text="검색" class="keyword-submit" />
    </div>
  </div>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import Input from '@/components/atoms/Input/Input.vue';
  import Label from '@/components/atoms/Label/Label.vue';
  import Button from '@/components/atoms/Button/Button.vue';
  import Select from '@/components/atoms/Select/Select.vue';
  import Option from '@/components/atoms/Option/Option.vue';

  export default defineComponent({
    name: 'Search',
    components: {
      SearchInput: Input,
      SearchLabel: Label,
      SearchSelect: Select,
      SearchOption: Option,
      SearchButton: Button,
    },
    setup() {
      const options: { text: string; value: string }[] = [
        { text: '제목', value: 'title' },
        { text: '작가', value: 'author' },
        { text: '사용자', value: 'user' },
      ];
      return {
        options,
      };
    },
  });
</script>

<style scoped>
  div.search-content {
    display: inline-flex;
    justify-content: center;
    align-content: center;
  }

  label.search-content-filter {
    padding-right: 10px;
  }
  label.search-content-filter select {
    font-family: var(--font-style-gaegu);
    color: var(--color-white);
  }

  div.search-content-keyword {
    box-shadow: var(--box-shadow);
    border: 1px solid var(--color-white);
  }
  div.search-content-keyword input {
    height: 30px;
    width: 170px;
  }
  div.search-content-keyword button.keyword-submit {
    padding: 0 10px;
    font-family: var(--font-style-gaegu);
    font-size: var(--font-text-small);
    color: var(--color-white);
  }
</style>
