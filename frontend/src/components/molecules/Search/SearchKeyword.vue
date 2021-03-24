<template>
  <div class="search-content">
    <search-label for="search-filter" class="search-content-filter" />
    <search-select
      id="search-filter"
      class="search-filter"
      :options="options"
    />
    <div class="search-content-keyword">
      <search-label for="keyword-input" class="keyword-input">
        <search-input id="keyword-input" />
        <icon
          v-if="useClearIcon"
          class="baseline-clear"
          iconName="baselineClear"
        />
      </search-label>
      <search-button text="검색" class="keyword-submit" />
    </div>
  </div>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import SearchInput from '@/components/atoms/Input/Input.vue';
  import SearchLabel from '@/components/atoms/Label/Label.vue';
  import SearchButton from '@/components/atoms/Button/Button.vue';
  import SearchSelect from '@/components/atoms/Select/Select.vue';
  import Icon from '@/components/atoms/Icon/Icon.vue';
  export default defineComponent({
    name: 'SearchKeyword',
    components: {
      SearchInput,
      SearchLabel,
      SearchSelect,
      SearchButton,
      Icon,
    },
    props: {
      useClearIcon: {
        type: Boolean,
        required: false,
        default: false,
      },
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
  div.search-content select {
    margin-right: 10px;
    font-family: var(--font-style-gaegu);
    color: var(--color-white);
    box-shadow: var(--box-shadow);
    height: 30px;
    padding: 0 10px;
  }
  div.search-content-keyword {
    box-shadow: var(--box-shadow);
    border: 1px solid var(--color-white);
  }
  div.search-content-keyword .keyword-input {
    position: relative;
  }
  div.search-content-keyword input {
    height: 30px;
    width: 170px;
  }
  div.search-content-keyword .baseline-clear {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    right: 8px;
    color: var(--color-dark-gray);
  }
  div.search-content-keyword button.keyword-submit {
    height: 30px;
    padding: 0 10px;
    font-family: var(--font-style-gaegu);
    font-size: var(--font-text-small);
    color: var(--color-white);
    background-color: var(--color-navy);
    border-radius: 0;
    box-shadow: none;
  }
</style>
