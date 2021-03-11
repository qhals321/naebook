import { shallowMount } from "@vue/test-utils";
import Button from "@/components/atoms/button/Button.vue";

describe('Button.vue', () => {
	it('버튼 메시지 출력', () => {
		const msg = 'test button';
		const wrapper = shallowMount(Button, {
			props: { msg },
		});
		expect(wrapper.text()).toMatch(msg);
	})
})
