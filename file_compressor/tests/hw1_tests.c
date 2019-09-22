#include <criterion/criterion.h>
#include <criterion/logging.h>
#include "const.h"
#include "custom.h"

Test(basecode_tests_suite, validargs_help_test) {
    int argc = 2;
    char *argv[] = {"bin/transplant", "-h", NULL};
    int ret = validargs(argc, argv);
    int exp_ret = 0;
    int opt = global_options;
    int flag = 0x1;
    cr_assert_eq(ret, exp_ret, "Invalid return for validargs.  Got: %d | Expected: %d",
		 ret, exp_ret);
    cr_assert_eq(opt & flag, flag, "Correct bit (0x1) not set for -h. Got: %x", opt);
}

Test(basecode_tests_suite, validargs_serialize_test) {
    int argc = 2;
    char *argv[] = {"bin/transplant", "-s", NULL};
    int ret = validargs(argc, argv);
    int exp_ret = 0;
    int opt = global_options;
    int flag = 0x2;
    cr_assert_eq(ret, exp_ret, "Invalid return for validargs.  Got: %d | Expected: %d",
		 ret, exp_ret);
    cr_assert(opt & flag, "Compress mode bit wasn't set. Got: %x", opt);
}

Test(basecode_tests_suite, validargs_error_test) {
    int argc = 3;
    char *argv[] = {"bin/transplant", "-s", "-c", NULL};
    int ret = validargs(argc, argv);
    int exp_ret = -1;
    cr_assert_eq(ret, exp_ret, "Invalid return for validargs.  Got: %d | Expected: %d",
		 ret, exp_ret);
}

Test(basecode_tests_suite, help_system_test) {
    char *cmd = "bin/transplant -h";

    // system is a syscall defined in stdlib.h
    // it takes a shell command as a string and runs it
    // we use WEXITSTATUS to get the return code from the run
    // use 'man 3 system' to find out more
    int return_code = WEXITSTATUS(system(cmd));

    cr_assert_eq(return_code, EXIT_SUCCESS,
                 "Program exited with %d instead of EXIT_SUCCESS",
		 return_code);
}

Test(basecode_tests_suite, path_init_test) {
    char *str = "bal";
    path_init(str);
    cr_assert_eq(strlen(path_buf), path_length, "Path buff length: %d instead of %d", strlen(path_buf), path_length);
    cr_assert_str_eq(path_buf, str, "Path buff: %s instead of %s", path_buf, str);
}

Test(basecode_tests_suite, path_push_test) {
    char *str = "ball";
    char *str2 = "erina";
    path_init(str);
    path_push(str2);
    cr_assert_str_eq(path_buf, "ball/erina", "Path buff: %s instead of %s", path_buf, "ball/erina");
}

Test(basecode_tests_suite, path_pop_test) {
    char *str = "ball";
    char *str2 = "erina";
    path_init(str);
    path_push(str2);
    path_pop();
    cr_assert_eq(strlen(path_buf), path_length, "Path buff length: %d instead of %d", strlen(path_buf), path_length);
    cr_assert_str_eq(path_buf, str, "Path buff: %s instead of %s", path_buf, str);
}
