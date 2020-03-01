package ryoryo.polishedlib.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModLogger
{
	private Logger logger;

	public ModLogger(String modId)
	{
		logger = LogManager.getLogger(References.MOD_ID);
	}

	/**
	 * ロガー
	 *
	 * @param event
	 */
	public void addLog(String log)
	{
		logger.log(Level.OFF, log);
	}

	public void addLog(Level lvl, String log)
	{
		logger.log(lvl, log);
	}

	public void addLog(Level lvl, String log, Object... params)
	{
		logger.log(lvl, log, params);
	}

	public void addLog(String log, Object... params)
	{
		logger.log(Level.OFF, log, params);
	}

	public void addLogThrowable(Throwable e, String log)
	{
		logger.log(Level.OFF, log, e);
	}

	public void addInfo(String info)
	{
		logger.info(info);
	}

	public void addInfo(String info, Object... params)
	{
		logger.info(info, params);
	}

	public void addInfoThrowable(Throwable e, String log)
	{
		logger.info(log, e);
	}

	public void addWarn(String warn)
	{
		logger.warn(warn);
	}

	public void addWarn(String warn, Object... params)
	{
		logger.warn(warn, params);
	}

	public void addWarnThrowable(Throwable e, String log)
	{
		logger.warn(log, e);
	}

	public void addTrace(String trace)
	{
		logger.trace(trace);
	}

	public void addTrace(String trace, Object... params)
	{
		logger.trace(trace, params);
	}

	public void addTraceThrowable(Throwable e, String log)
	{
		logger.trace(log, e);
	}

	public void addFatal(String fatal)
	{
		logger.fatal(fatal);
	}

	public void addFatal(String fatal, Object... params)
	{
		logger.fatal(fatal, params);
	}

	public void addFatalThrowable(Throwable e, String log)
	{
		logger.fatal(log, e);
	}

	public void addDebug(String debag)
	{
		logger.debug(debag);
	}

	public void addDebug(String debag, Object... params)
	{
		logger.debug(debag, params);
	}

	public void addDebugThrowable(Throwable e, String log)
	{
		logger.debug(log, e);
	}

	public void addError(String error)
	{
		logger.error(error);
	}

	public void addError(String error, Object... params)
	{
		logger.error(error, params);
	}

	public void addErrorThrowable(Throwable e, String log)
	{
		logger.error(log, e);
	}
}